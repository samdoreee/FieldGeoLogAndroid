package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.samdoreee.fieldgeolog.R

class OneRecordDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_record_detail)


        val back_to_home_btn = findViewById<ImageButton>(R.id.back_to_home_btn)
        back_to_home_btn.setOnClickListener {
            val intent = Intent(this, OneRecordActivity::class.java)
            startActivity(intent)
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
    }
}