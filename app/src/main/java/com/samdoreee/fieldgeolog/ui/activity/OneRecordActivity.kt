package com.samdoreee.fieldgeolog.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.OneRecordModel
import com.samdoreee.fieldgeolog.ui.adapter.MyRecordSpotListRvAdapter

class OneRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_record)

        val num_preview = mutableListOf<OneRecordModel>()
        num_preview.add(OneRecordModel(1))
        num_preview.add(OneRecordModel(2))
        num_preview.add(OneRecordModel(3))
        num_preview.add(OneRecordModel(4))

        val spotlist = findViewById<RecyclerView>(R.id.spotlist)
        val spotlistadapter = MyRecordSpotListRvAdapter(this, num_preview)
        spotlist.adapter = spotlistadapter


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
    }
}