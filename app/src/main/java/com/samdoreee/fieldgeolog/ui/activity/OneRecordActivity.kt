package com.samdoreee.fieldgeolog.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.data.model.OneRecordModel
import com.samdoreee.fieldgeolog.network.ArticleResponse
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.PersonalRecordResponse
import com.samdoreee.fieldgeolog.network.SpotResponse
import com.samdoreee.fieldgeolog.ui.adapter.CommunitySpotListRvAdapter
import com.samdoreee.fieldgeolog.ui.adapter.MyRecordSpotListRvAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class OneRecordActivity : AppCompatActivity(), CoroutineScope {


    private val job = Job()

    override val coroutineContext = Dispatchers.Main + job

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_record)
        val myId: Long = intent.getLongExtra("myId", 0L)
        val recordId: Long = intent.getLongExtra("recordId", 0L)

        launch {
            try {
                val response2: Response<PersonalRecordResponse> = withContext(Dispatchers.IO) {
                    GeoApi.retrofitService.getRecord(recordId = recordId)
                }

                if (response2.isSuccessful) {
                    val personalRecordResponse: PersonalRecordResponse? = response2.body()

                    Log.d(Constants.TAG, "내기 : ${response2.body()}")
                    if (personalRecordResponse != null) {
                        Log.d(
                            Constants.TAG,
                            "스팟수 : ${personalRecordResponse.spotResponseDTOList.count()}"
                        )


                        val record_title = findViewById<TextView>(R.id.record_title)
                        val record_location = findViewById<TextView>(R.id.record_location)
                        val record_date = findViewById<TextView>(R.id.record_date)
                        val record_weather = findViewById<TextView>(R.id.record_weather)


                        record_title.text = personalRecordResponse.recordTitle
                        record_location.text =
                            "(" + personalRecordResponse.spotResponseDTOList[0].latitude.toString() + ", " + personalRecordResponse.spotResponseDTOList[0].longitude + ")"
                        record_date.text = personalRecordResponse.createDT.take(10)
                        record_weather.text =
                            personalRecordResponse.spotResponseDTOList[0].weatherInfo

                        var num_preview = mutableListOf<OneRecordModel>()
                        for (i in 1..personalRecordResponse.spotResponseDTOList.count()) {
                            num_preview.add(OneRecordModel(personalRecordResponse.spotResponseDTOList[i - 1].id))
                        }

                        /*spot num list-중앙 RV 어댑터 연결*/
                        val spotlistadapter =
                            MyRecordSpotListRvAdapter(
                                this@OneRecordActivity,
                                num_preview,
                                myId,
                                recordId
                            )
                        val spotlist = findViewById<RecyclerView>(R.id.spotlist)
                        spotlist.adapter = spotlistadapter


//                        val spotlistadapter = MyRecordSpotListRvAdapter(this@OneRecordActivity, num_preview, myId, recordId)
//                        spotlist.adapter = spotlistadapter


                    } else {
                        // 요청이 실패했을 때의 처리
                        Log.d(Constants.TAG, "오류ㅜ오류 ")
                        val errorBody = response2.errorBody()?.string()
                        Log.d(Constants.TAG, "오류ㅜ : $errorBody")
                        // 에러 메시지 등을 처리
                    }
                }




                val mapView = findViewById<MapView>(R.id.mapView)
                mapView.start(object : MapLifeCycleCallback() {
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

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}