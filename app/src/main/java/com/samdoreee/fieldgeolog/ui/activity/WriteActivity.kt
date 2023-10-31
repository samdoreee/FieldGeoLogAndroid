package com.samdoreee.fieldgeolog.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.samdoreee.fieldgeolog.databinding.ActivityWriteBinding
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.Spot
import com.samdoreee.fieldgeolog.network.SpotRequest
import kotlinx.coroutines.runBlocking


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
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val PERMISSIONS_REQUEST = 100
    private val cameraButton = 100
    private var photoUri: Uri? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkPermissions(PERMISSIONS, PERMISSIONS_REQUEST)
        /*받아온 spot에서 데이터를 추출!*/

        binding.btnSave.setOnClickListener {
            save()
            finish()
            startActivity(Intent(this, OldNewRecordActivity::class.java))
        }

        /*binding.camerabtn.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, cameraButton)
            }
        }*/
    }

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

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                cameraButton -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    *//*memo_photo0 = data?.extras?.get("data") as String*//*
                    binding.photo0.setImageBitmap(imageBitmap)
                }
            }
        }
    }
*/

    private fun checkPermissions(permissions: Array<String>, permissionsRequest: Int): Boolean {
        val permissionList: MutableList<String> = mutableListOf()
        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission)
            }
        }
        if (permissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(this,
                permissionList.toTypedArray(),
                PERMISSIONS_REQUEST)
            return false
        }
        return true
    }

/*    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for(result in grantResults){
            if(result != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "권한 승인 부탁드립니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }*/

}


