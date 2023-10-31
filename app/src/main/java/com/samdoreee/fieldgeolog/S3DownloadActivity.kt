package com.samdoreee.fieldgeolog

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.regions.Regions
import java.io.*import java.io.FileInputStream

class S3DownloadActivity : AppCompatActivity(), S3MediaUploader.DownloadListener {
    var bt_download: Button? = null
    var image: ImageView? = null
    val AWSAccessKeyId: String = "AKIAWTFYC3FPRCM4TVFA"
    val SecretAccessKey: String = "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"
    // 파일명은 임시, 이거를 DB에서 불러와서 갈아끼우면 됨
    var fileName = "0dde490d-6d4a-4333-9be2-049a9dab58ef_Screenshot_20230930-173737_My Files.jpg"
    val sessionToken: String = ""
    val bucketName: String = "fieldgeolog/uploaded"
    val regions: Regions = Regions.AP_NORTHEAST_2

    private val SELECT_PICTURE = 1
    var count = 0
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s3_download)
        bt_download = findViewById(R.id.bt_download)
        image = findViewById(R.id.image)
        bt_download!!.setOnClickListener {
            downloadFile(fileName)
        }
    }

    fun downloadFile(fileName: String){
        S3MediaUploader().downloadFileFromS3(
            this,
            fileName,
            AWSAccessKeyId,
            SecretAccessKey,
            bucketName,
            regions,
            object : S3MediaUploader.DownloadListener {
                override fun onDownloadCompleted(file: File?) {
                    // 다운로드 성공 시 처리
                    // file에 다운로드된 파일이 저장되어 있음
                    var imageStream: InputStream? = null
                    try {
                        imageStream = FileInputStream(file)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                    if (imageStream != null) {
                        count++
                        image!!.setImageBitmap(BitmapFactory.decodeStream(imageStream))
                    }
                }

                override fun onDownloadFailed() {
                    // 다운로드 실패 시 처리
                }
            }
        )
    }




    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
//                    OnPictureSelect(data)
                }
            }
        }
    }



    override fun onProgressUpdate(id: Int, current: Int) {
        super.onProgressUpdate(id, current)
    }

    override fun onDownloadFailed() {
        TODO("Not yet implemented")
    }

    override fun onDownloadCompleted(file: File?) {
        super.onDownloadCompleted(file)
        TODO("Not yet implemented")
    }
}