package com.samdoreee.fieldgeolog.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.databinding.ActivityWriteBinding
import com.samdoreee.fieldgeolog.network.FileRequest
import com.samdoreee.fieldgeolog.network.FileResponse
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.MemoRequest
import com.samdoreee.fieldgeolog.network.MemoResponse
import com.samdoreee.fieldgeolog.network.PersonalRecordResponse
import com.samdoreee.fieldgeolog.network.Spot
import com.samdoreee.fieldgeolog.network.SpotRequest
import com.samdoreee.fieldgeolog.network.SpotResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


@Suppress("DEPRECATION")
class WriteActivity : AppCompatActivity(), CoroutineScope {
    private val job = Job()
    override val coroutineContext = Dispatchers.Main + job

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

    var myId: Long = 0L
    var recordId: Long = 0L
    val latitudeByIntent: Double = intent?.getDoubleExtra("latitude", 0.0) ?: 0.0
    val longitudeByIntent: Double = intent?.getDoubleExtra("longitude", 0.0) ?: 0.0

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkPermissions(PERMISSIONS, PERMISSIONS_REQUEST)
        /*받아온 spot에서 데이터를 추출!*/
        myId = intent.getLongExtra("myId", 0L)
        recordId = intent.getLongExtra("recordId", 0L)

        Log.d(Constants.TAG, "write 나랑 레코드 $myId $recordId")
        binding.btnSave.setOnClickListener {
            Log.d(Constants.TAG, "write 나랑 레코드2 $myId $recordId")
            save()
            val intent = Intent(this, NewRecordActivity::class.java)
            intent.putExtra("myId", myId)
            intent.putExtra("recordId", recordId)
            startActivity(intent)
            finish()
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
        val description = binding.memo.text.toString()

        launch {

            try {
                val newSpot = SpotRequest(
                    latitude = intent.getDoubleExtra("latitude", 0.0),
                    longitude = intent.getDoubleExtra("longitude", 0.0),
                    strike = strike,
                    rockType = rockType,
                    geoStructure = geoStructure,
                    dip = dip,
                    direction = direction
                )
                var spotResponse: SpotResponse? = null
                var memoResponse: MemoResponse? = null
                Log.d(Constants.TAG, "newSpot = ${newSpot}")
                val response: Response<SpotResponse> = withContext(Dispatchers.IO) {
                    GeoApi.retrofitService.addSpot(recordId, newSpot)
                }
                if (response.isSuccessful) {
                    spotResponse = response.body()!!
                    if (spotResponse != null) {


                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.d(Constants.TAG, "오류ㅜㅡㅜ : $errorBody")
                    }
                }


                val newMemo = MemoRequest(
                    description = description
                )
                val response2: Response<MemoResponse> = withContext(Dispatchers.IO) {
                    GeoApi.retrofitService.addMemo(recordId, spotResponse!!.id, newMemo)
                }
                if (response2.isSuccessful) {
                    memoResponse = response2.body()!!
                    if (memoResponse != null) {


                    } else {
                        val errorBody = response2.errorBody()?.string()
                        Log.d(Constants.TAG, "오류ㅜㅡㅜ : $errorBody")
                    }
                }
                val pictureCnt = 1
                for (i in 1..pictureCnt) {
                    val newFile = FileRequest(
                        fileFolder = "",
                        fileName = "1210b3e5-8fd8-4421-b912-80ce55cad38f_Screenshot_20230930-173737_My Files.jpg"
                    )
                    val response3: Response<FileResponse> = withContext(Dispatchers.IO) {
                        GeoApi.retrofitService.addPicture(
                            recordId,
                            spotResponse!!.id,
                            memoResponse!!.id,
                            newFile
                        )
                    }
                    if (response3.isSuccessful) {
                        val fileResponse: FileResponse? = response3.body()
                        if (fileResponse != null) {
                            Log.d(Constants.TAG, "사진 굿 : ${fileResponse.id}}")
                        } else {
                            val errorBody = response3.errorBody()?.string()
                            Log.d(Constants.TAG, "오류ㅜㅡㅜ : $errorBody")
                        }
                    }
                }


            } catch (e: Exception) {
                // 예외 처리
            }


        }

//        project_thumbnail = "samdoree"
//        val thumbnail = project_thumbnail
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
            ActivityCompat.requestPermissions(
                this,
                permissionList.toTypedArray(),
                PERMISSIONS_REQUEST
            )
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
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}


