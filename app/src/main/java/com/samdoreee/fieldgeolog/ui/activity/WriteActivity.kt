package com.samdoreee.fieldgeolog.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.samdoreee.fieldgeolog.MediaScanner
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.databinding.ActivityWriteBinding
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.Spot
import com.samdoreee.fieldgeolog.network.SpotRequest
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Suppress("DEPRECATION")
class WriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityWriteBinding
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var project_thumbnail: String
    private lateinit var dip: EditText
    private lateinit var strike: EditText
    private lateinit var rocktype: EditText
    private lateinit var geo_struct: EditText
    private lateinit var memo_photo0: String
    private lateinit var content: String

    private lateinit var spot: Spot

    val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    val PERMISSIONS_REQUEST = 100
    private val cameraButton = 100
    private var photoUri: Uri? = null
    private lateinit var currentPhotoPath: String
    private lateinit var currentPhotoName: String
    private val REQUEST_IMAGE_CAPTURE = 672
    private var imageFilePath: String? = null
    private val REQUEST_IMAGE_CAPTURE_1 = 1
    private val REQUEST_IMAGE_CAPTURE_2 = 2
    private val REQUEST_IMAGE_CAPTURE_3 = 3

    private lateinit var mMediaScanner: MediaScanner

    lateinit var bitmap: Bitmap
    lateinit var selectedImageView: ImageView


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            save()
            finish()
            startActivity(Intent(this, NewRecordActivity::class.java))
        }

        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setRationaleMessage("카메라 권한이 필요합니다.")
            .setDeniedMessage("거부하셨습니다.")
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .check()

        checkPermissions(PERMISSIONS, PERMISSIONS_REQUEST)

        binding.click.setOnClickListener {
            selectedImageView = binding.thumbnail1
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activityResult.launch(intent)
        }
        binding.click2.setOnClickListener {
            selectedImageView = binding.thumbnail2
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activityResult.launch(intent)
        }
        binding.click3.setOnClickListener {
            selectedImageView = binding.thumbnail3
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activityResult.launch(intent)
        }

    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val extras = result.data!!.extras
            val bitmap = extras?.get("data") as Bitmap
            selectedImageView?.setImageBitmap(bitmap)
        }
    }


    private val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(applicationContext, "권한이 허용됨", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            Toast.makeText(applicationContext, "권한이 거부됨", Toast.LENGTH_SHORT).show()
        }
    }
    private fun checkPermissions(permissions: Array<String>, permissionsRequest: Int): Boolean {
        val permissionList: MutableList<String> = mutableListOf()
        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission)
            }
        }
        if (permissionList.isNotEmpty()) {
            Log.d("permissionlistcnt", "${permissionList.count()}")
            ActivityCompat.requestPermissions(this,
                permissionList.toTypedArray(),
                permissionsRequest)
            return false
        }
        Log.d("permissionchecking?", "모든 권한이 드디어 승인됐다!")
        return true
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST) {
            var allPermissionsGranted = true
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false
                    break
                }
            }
            if (allPermissionsGranted) {
                Log.d("allpermissiongranted", "모든 권한 승인 완료!")
            }
            else {
                Toast.makeText(this, "권한 승인 부탁드립니다! :>", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    /*----------[혜림이가 만든 카메라 코드 영역 끝]----------*/
    private fun save() {
        val match = Regex("(\\d+)([A-Z]+)").find(binding.strike.text.toString())!!
        val strike = match.destructured.component1().toInt()
        val direction = match.destructured.component2()
        val dip = binding.dip.text.toString().toInt()
        val rockType = binding.rocktype.text.toString()
        val geoStructure = binding.geoStruct.text.toString()

        runBlocking {
            val newSpot = SpotRequest(
                latitude = intent.getDoubleExtra("latitude", 0.0),
                longitude = intent.getDoubleExtra("longitude", 0.0),
                strike = strike,
                rockType = rockType,
                geoStructure = geoStructure,
                dip = dip,
                direction = direction
            )
            println("newSpot = ${newSpot}")
            GeoApi.retrofitService.addSpot(newSpot)
        }

        project_thumbnail = "samdoree"
        val thumbnail = project_thumbnail
    }
}