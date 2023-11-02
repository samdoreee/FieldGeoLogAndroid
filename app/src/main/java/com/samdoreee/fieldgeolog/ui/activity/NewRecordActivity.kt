package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.databinding.ActivityNewRecordBinding
import com.samdoreee.fieldgeolog.network.FileRequest
import com.samdoreee.fieldgeolog.network.FileResponse
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.MemoRequest
import com.samdoreee.fieldgeolog.network.MemoResponse
import com.samdoreee.fieldgeolog.network.PersonalRecordRequest
import com.samdoreee.fieldgeolog.network.PersonalRecordResponse
import com.samdoreee.fieldgeolog.network.SpotRequest
import com.samdoreee.fieldgeolog.network.SpotResponse
import com.samdoreee.fieldgeolog.network.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class NewRecordActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()
    override val coroutineContext = Dispatchers.Main + job
    private lateinit var binding: ActivityNewRecordBinding
    private var myId: Long = 0L
    private var recordId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding 초기화는 한 번만 실행합니다.
        binding = ActivityNewRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent에서 myId와 recordId를 가져옵니다.
        myId = intent.getLongExtra("myId", 0L)
        recordId = intent.getLongExtra("recordId", 0L)

        Log.d(Constants.TAG, "인텐트 넘어옴 $myId $recordId")

        // 여기서 클릭 리스너를 설정합니다.
        binding.backToHomeBtn.setOnClickListener {
            save()
            val intent = Intent(this@NewRecordActivity, MainActivity::class.java)
            startActivity(intent)
        }
        binding.addNewSpotBtn.setOnClickListener {
            save()
            Log.d(Constants.TAG, "나랑 레코드 $myId $recordId")
            val intent = Intent(this@NewRecordActivity, WriteActivity::class.java)
            intent.putExtra("myId", myId)
            intent.putExtra("recordId", recordId)
            intent.putExtra("latitude", 36.64)
            intent.putExtra("longitude", 127.49)

            startActivity(intent)
        }
        binding.onGeomapBtn.setOnClickListener {
            save()
            val intent = Intent(this@NewRecordActivity, GeoMapActivity::class.java)
            startActivity(intent)
            Toast.makeText(
                this@NewRecordActivity,
                "지질도 모드로 전환합니다",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnSave.setOnClickListener {
            save()
            finish()
            val intent = Intent(this@NewRecordActivity, MainActivity::class.java)
            intent.putExtra("myId", myId)
            intent.putExtra("recordId", recordId)
            startActivity(intent)
        }

        launch {
            try {

                val response: Response<PersonalRecordResponse> = withContext(Dispatchers.IO) {
                    if (recordId == 0L) {
                        Log.d(Constants.TAG, "레코드아이디 0")
                        GeoApi.retrofitService.addRecord(PersonalRecordRequest(recordTitle = "dd", userId = myId))
                    } else {
                        Log.d(Constants.TAG, "레코드아이디 $recordId")
                        GeoApi.retrofitService.getRecord(recordId = recordId)
                    }
                }

                if (response.isSuccessful) {
                    val personalRecordResponse: PersonalRecordResponse? = response.body()

                    Log.d(Constants.TAG, "내기 : ${response.body()}")
                    if (personalRecordResponse != null) {
                        Log.d(
                            Constants.TAG,
                            "스팟수는 : ${personalRecordResponse.spotResponseDTOList.count()}"
                        )
                        recordId = personalRecordResponse.id


                    } else {
                        // 요청이 실패했을 때의 처리
                        Log.d(Constants.TAG, "오류ㅜ류 ")
                        val errorBody = response.errorBody()?.string()
                        Log.d(Constants.TAG, "오류ㅜㅡ : $errorBody")
                        // 에러 메시지 등을 처리
                    }
                }
                Log.d(Constants.TAG, "오류ㅜㅡㅜ : ${response.errorBody()?.string()}")

                /*mapView에 배경 kakaoMap 호출*/
                binding.mapView.start(object : MapLifeCycleCallback() {
                    override fun onMapDestroy() {
                        // 지도 API 가 정상적으로 종료될 때 호출됨
                        Log.d("KaKaoMap", "Successfully Finished")
                    }

                    override fun onMapError(error: Exception) {
                        // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
                        try {
                            // 여기서 예외를 캐치하고 로그에 기록할 수 있습니다.
                            Log.d("KaKaoMap", "Error occurred: ${error.message}")
                        } catch (e: Exception) {
                            // 예외가 발생한 경우에 대한 추가적인 조치를 취할 수 있습니다.
                            e.printStackTrace()
                        }
                    }
                }, object : KakaoMapReadyCallback() {
                    override fun onMapReady(kakaoMap: KakaoMap) {
                        // 인증 후 API 가 정상적으로 실행될 때 호출됨
                        Log.d("KaKaoMap", "Successfully Working! U R Lucky :D")
                    }
                })
            } catch (e: Exception) {
                // 예외 처리
            }
        }
    }

    private fun save() {
        val title = binding.recordTitle.text.toString()

        launch {
            try {
                val modifiedRecord = PersonalRecordRequest(
                    recordTitle = title,
                    userId = myId
                )
                Log.d(Constants.TAG, "newSpot = ${modifiedRecord}")
                val response: Response<PersonalRecordResponse> = withContext(Dispatchers.IO) {
                    GeoApi.retrofitService.modifyRecord(recordId, modifiedRecord)
                }
                if (response.isSuccessful) {
                    Log.d(Constants.TAG, "이즈석세스풀 = ${modifiedRecord}")
                    val recordResponse: PersonalRecordResponse? = response.body()
                    if (recordResponse != null) {
                        Log.d(Constants.TAG, "정상변경 : $recordResponse")
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.d(Constants.TAG, "오류ㅜㅡㅜ : $errorBody")
                    }
                }
                Log.d(Constants.TAG, "newSpot4 = ${response.errorBody()?.string()}")
            } catch (e: Exception) {
                Log.e(Constants.TAG, "예외 발생: ${e.message}")
                Log.e(Constants.TAG, Log.getStackTraceString(e))
            }
        }
    }
}