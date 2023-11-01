package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.samdoreee.fieldgeolog.databinding.ActivityNewRecordBinding


class NewRecordActivity : AppCompatActivity() {
    
    private lateinit var binding : ActivityNewRecordBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.samdoreee.fieldgeolog.R.layout.activity_new_record)
        /*데이터 binding 초기화*/
        binding = ActivityNewRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*각 버튼 페이지 이동 연결*/
        binding.backToHomeBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.addNewSpotBtn.setOnClickListener {
            val intent = Intent(this, RecordWriteActivity::class.java)
            startActivity(intent)
        }
        binding.onGeomapBtn.setOnClickListener {
            val intent = Intent(this, GeoMapActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "지질도 모드로 전환합니다", Toast.LENGTH_SHORT).show()
        }
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

    }
}
