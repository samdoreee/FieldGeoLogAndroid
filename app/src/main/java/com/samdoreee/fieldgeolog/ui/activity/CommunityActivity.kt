package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.CommunityModel
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import com.samdoreee.fieldgeolog.network.ArticleResponse
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.ui.adapter.CommunityAdapter
import com.samdoreee.fieldgeolog.ui.adapter.MyRecordAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class CommunityActivity : AppCompatActivity() , CoroutineScope {

    private val job = Job()

    override val coroutineContext = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        val myId: Long = intent.getLongExtra("myId", 0L)  // 0L은 기본값
        Log.d(Constants.TAG, "인텐트 : $myId")

        launch {
            try {
                val response2: Response<List<ArticleResponse>> = withContext(Dispatchers.IO) {
                    GeoApi.retrofitService.getAllArticles()
                }

                if (response2.isSuccessful) {
                    val articleResponse: List<ArticleResponse>? = response2.body()?.filter { it.userId != myId }

                    Log.d(Constants.TAG, "남의 글 : ${response2.body()}")
                    if (articleResponse != null) {
                        Log.d(Constants.TAG, "남의 글 널아님 : $articleResponse")

                        val communityModels: MutableList<CommunityModel> = articleResponse.map { it.convertToCommunityModel() }.toMutableList()
                        val communitylistadapter = CommunityAdapter(communityModels)
                        val communitylist = findViewById<ListView>(R.id.communitylistview)
                        communitylist.adapter = communitylistadapter

                        communitylist.setOnItemClickListener { parent, view, position, id ->
                            val intent = Intent(this@CommunityActivity, OneRecordActivity::class.java)
                            intent.putExtra("myId", myId)
                            startActivity(intent)
                        }

                    }
                } else {
                    // 요청이 실패했을 때의 처리
                    val errorBody = response2.errorBody()?.string()
                    Log.d(Constants.TAG, "오류ㅜ : $errorBody")
                    // 에러 메시지 등을 처리
                }
            } catch (e: Exception) {
                // 예외 처리
            }
            Log.d("", "")
        }
    }
}