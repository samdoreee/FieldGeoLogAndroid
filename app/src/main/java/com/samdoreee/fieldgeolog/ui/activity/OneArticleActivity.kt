package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.CommentModel
import com.samdoreee.fieldgeolog.data.model.OneRecordModel
import com.samdoreee.fieldgeolog.databinding.ActivityNewRecordBinding
import com.samdoreee.fieldgeolog.databinding.ActivityOneArticleBinding
import com.samdoreee.fieldgeolog.ui.adapter.CommentRvAdapter
import com.samdoreee.fieldgeolog.ui.adapter.OneArticleAdapter
import com.samdoreee.fieldgeolog.ui.adapter.SpotListRvAdapter

class OneArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_article)
        val back_to_home_btn = findViewById<ImageButton>(R.id.back_to_home_btn)
        back_to_home_btn.setOnClickListener {
            val intent = Intent(this, CommunityActivity::class.java)
            startActivity(intent)
        }
        val num_preview = mutableListOf<OneRecordModel>()
        num_preview.add(OneRecordModel(1))
        num_preview.add(OneRecordModel(2))
        num_preview.add(OneRecordModel(3))
        num_preview.add(OneRecordModel(4))
        num_preview.add(OneRecordModel(5))
        num_preview.add(OneRecordModel(6))
        num_preview.add(OneRecordModel(7))

        /*spot num list-중앙 RV 어댑터 연결*/
        val spotlistadapter = SpotListRvAdapter(this, num_preview)
        val spotlist = findViewById<RecyclerView>(R.id.spotlist)
        spotlist.adapter = spotlistadapter

        var comment_data = mutableListOf<CommentModel>()
        comment_data.add(CommentModel(1, "댓글 내용은 다음과 같습니다\n언니예뻐요! 정현언니! 앙앙",
            "2022.11.22", "풍혜림", R.drawable.profile22))
        comment_data.add(CommentModel(2, "댓글 내용은 다음과 같습니다 진영 더바보\n언니예뻐요! 정현언니! 앙앙",
            "2022.11.22", "풍혜림", R.drawable.profile22))
        comment_data.add(CommentModel(3, "댓글 내용은 다음과 같습니다\n언니예뻐요! 정현언니! 앙앙",
            "2022.11.22", "풍혜림", R.drawable.profile22))
        comment_data.add(CommentModel(4, "댓글 내용은 다음과 같습니다 정현바보\n언니예뻐요! 정현언니! 앙앙",
            "2022.11.22", "풍혜림", R.drawable.profile22))
        comment_data.add(CommentModel(5, "댓글 내용은 다음과 같습니다\n 메롱메롱 언니예뻐요! 정현언니! 앙앙",
            "2022.11.22", "풍혜림", R.drawable.profile22))
        /*comment list-하위👋 RV 어댑터 연결*/
        val commentlistadapter = CommentRvAdapter(this, comment_data)
        val commentList = findViewById<RecyclerView>(R.id.commentlistview)
        commentList.layoutManager = LinearLayoutManager(this)
        commentList.adapter = commentlistadapter


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