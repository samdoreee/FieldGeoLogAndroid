package com.samdoreee.fieldgeolog

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.databinding.ActivityMemoBinding
import com.samdoreee.fieldgeolog.databinding.ActivityWriteBinding
import com.samdoreee.fieldgeolog.record.Memo
import com.samdoreee.fieldgeolog.record.Project
import com.samdoreee.fieldgeolog.record.Record


@Suppress("DEPRECATION")
class WriteActivity : AppCompatActivity() {
    lateinit var binding : ActivityWriteBinding
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var project_title: EditText
    private lateinit var project_date: EditText
    private lateinit var project_location: EditText
    private lateinit var project_weather: EditText
    private lateinit var project_thumbnail: String
    private lateinit var dip: EditText
    private lateinit var strike: EditText
    private lateinit var rocktype: EditText
    private lateinit var geo_struct: EditText
    private lateinit var memo_photo0: String
    private lateinit var content: String
    private val projectList = TempMemory.tempprojectmemory
    private val recordList = TempMemory.temprecordmemory
    private val memoList = TempMemory.tempmemomemory

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
/*      super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)*/

        binding = ActivityWriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkPermissions(PERMISSIONS, PERMISSIONS_REQUEST)

        binding.btnSave.setOnClickListener {
            save()
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.btnCancel.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.camerabtn.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, cameraButton)
            }
        }
    }


    private fun save() {
        val title = project_title.text.toString()
        val location = project_location.text.toString()
        val weather = project_weather.text.toString()
        val strike = strike.text.toString()
        val dip = dip.text.toString()
        val rocktype = rocktype.text.toString()
        val geo_struct = geo_struct.text.toString()
        val date = project_date.text.toString()
        project_thumbnail = "samdoree"
        val thumbnail = project_thumbnail
/*
        val content = content.text.toString()
*/

        val rec_add = Record(title,
            thumbnail,
            date,
            location,
            weather,
            strike,
            dip,
            rocktype,
            geo_struct)
        recordList.add(rec_add)
        Log.d("rec_add", "{$rec_add}")

        val project_add = Project(rec_add.project_title,
            rec_add.project_date,
            rec_add.project_location,
            rec_add.project_thumbnail)
        projectList.add(project_add)
        Log.d("project_add", "{$project_add}")

        val memo_add = Memo(content)
        memoList.add(memo_add)
        Log.d("memo_add", "{$memoList}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                cameraButton -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    /*memo_photo0 = data?.extras?.get("data") as String*/
                    binding.photo0.setImageBitmap(imageBitmap)
                }
            }
        }
    }


    private fun checkPermissions(permissions: Array<String>, permissionsRequest: Int): Boolean {
        val permissionList : MutableList<String> = mutableListOf()
        for(permission in permissions){
            val result = ContextCompat.checkSelfPermission(this, permission)
            if(result != PackageManager.PERMISSION_GRANTED){
                permissionList.add(permission)
            }
        }
        if(permissionList.isNotEmpty()){
            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), PERMISSIONS_REQUEST)
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