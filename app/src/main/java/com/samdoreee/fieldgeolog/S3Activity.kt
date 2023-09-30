package com.samdoreee.fieldgeolog

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
//import com.softsuave.s3bucketmediaupload.R
//import com.softsuave.s3bucketmediaupload.S3MediaUploader
import com.softsuave.s3media_files_uploader.utils.AmazonS3Utils
import java.io.*

class S3Activity : AppCompatActivity(), S3MediaUploader.UploadListener {
    var bt_upload: Button? = null
    var bt_select: Button? = null
    var image: ImageView? = null
    var s3uploaderObj: S3MediaUploader? = null
    var urlFromS3: String? = null
    var imageUri: Uri? = null
    var filePath: String? = null
    var docFile: File? = null
    private val TAG = S3Activity::class.java.canonicalName
    private val SELECT_PICTURE = 1
    var count = 0
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s3)
        bt_upload = findViewById(R.id.bt_upload)
        bt_select = findViewById(R.id.bt_select)
        image = findViewById(R.id.image)
        bt_select!!.setOnClickListener { isStoragePermissionGranted }
        bt_upload!!.setOnClickListener {
            uploadFileToS3(imageUri)
        }
    }
    val isStoragePermissionGranted: Unit
        get() {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    chooseImage()
                } else {
                    Log.v(TAG, "Permission is revoked")
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1
                    )
                }
            } else {
                Log.v(TAG, "Permission is granted")
                chooseImage()
            }
        }

    private fun chooseImage() {
        val intent = Intent()
        intent.setType("*/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            chooseImage()
            Log.e(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
        } else {
            Log.e(TAG, "Please click again and select allow to choose profile picture")
        }
    }

    fun OnPictureSelect(data: Intent) {
        imageUri = data.getData()
        filePath =  getFilePathFromContentUri(applicationContext, imageUri!!) // AmazonS3Utils().getFilePathFromURI
        Log.e(TAG, "imageUri : " + imageUri.toString())
        Log.e(TAG, "filepath : " + filePath.toString())
        var imageStream: InputStream? = null
        try {
            imageStream = imageUri?.let { getContentResolver().openInputStream(it) }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        if (imageStream != null) {
            count++
            image!!.setImageBitmap(BitmapFactory.decodeStream(imageStream))
        }
    }

    fun getFilePathFromContentUri(context: Context, uri: Uri): String? {
        var filePath: String? = null

        // Check for API level
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            // Get the real path from the URI using the deprecated method
            val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex: Int = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    filePath = it.getString(columnIndex)
                }
            }
        } else {
            // Use the new SAF (Storage Access Framework) method
            val wholeID = DocumentsContract.getDocumentId(uri)
            val id: String = wholeID.split(":")[1]

            val column: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
            val sel: String = MediaStore.Images.Media._ID + "=?"
            val cursor: Cursor? = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, arrayOf(id), null
            )

            cursor?.use {
                val columnIndex: Int = it.getColumnIndex(column[0])
                if (it.moveToFirst()) {
                    filePath = it.getString(columnIndex)
                }
            }
        }

        return filePath
    }

    fun uploadFileToS3(imageUri: Uri?) {
       // val path = getFilePathFromURI(imageUri)
        val AWSAccessKeyId: String = "AKIAWTFYC3FPRCM4TVFA"
        val SecretAccessKey: String = "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"
        val sessionToken: String = ""
        val bucketName: String = "fieldgeolog/uploaded"
        val regions: Regions = Regions.AP_NORTHEAST_2

        if (filePath != null) {
            Log.e(TAG, "notNullFilePath: $filePath")
            filePath?.let {
                S3MediaUploader().uploadMediaFile(this,
                    it,
                    AWSAccessKeyId,
                    SecretAccessKey,
                    "",
                    bucketName,
                    regions,
                    object : S3MediaUploader.UploadListener {
                        override fun onUploadFailed() {
                            Log.d(TAG, "onUploadFailed: #####")
                        }

                        override fun onUploadCompleted(fileName: String?) {
                            Log.d(TAG, "onUploadCompleted: $fileName ")
                            val credentials =
                                BasicAWSCredentials(
                                    AWSAccessKeyId,
                                    SecretAccessKey
                                )
                            val url = AmazonS3Utils().getUrl(bucketName, fileName, credentials)

                            Log.d(TAG, "getUrl: $url ")
                        }

                        override fun onProgressUpdate(id: Int, current: Int) {
                            super.onProgressUpdate(id, current)
                            Log.d(TAG, "Progress: $current")
                        }

                    })
            }

        } else {
            Toast.makeText(this, "Null Path", Toast.LENGTH_SHORT).show()
        }
    }


    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    OnPictureSelect(data)
                }
            }
        }
    }

    override fun onProgressUpdate(id: Int, current: Int) {
        super.onProgressUpdate(id, current)
    }

    override fun onUploadFailed() {
        TODO("Not yet implemented")
    }

    override fun onUploadCompleted(fileName: String?) {
        TODO("Not yet implemented")

        // 파일 다운로드 예시
        S3MediaUploader().downloadFileFromS3(
            this,
            fileName!!,
            "AKIAWTFYC3FPRCM4TVFA",
            "H7pDlBF8pMJRloQ3pwuqLvRuxHhvt+v+uEEfCAkw",
            "fieldgeolog/uploaded",
            Regions.AP_NORTHEAST_2,
            object : S3MediaUploader.DownloadListener {
                override fun onDownloadCompleted(file: File?) {
                    // 다운로드 성공 시 처리
                    // file에 다운로드된 파일이 저장되어 있음
                    Log.d(TAG, "downloaded file : $file")
                }

                override fun onDownloadFailed() {
                    // 다운로드 실패 시 처리
                }
            }
        )
    }
}