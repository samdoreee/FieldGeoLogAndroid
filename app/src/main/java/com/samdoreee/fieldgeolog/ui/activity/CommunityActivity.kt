package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.CommunityModel
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import com.samdoreee.fieldgeolog.network.ArticleResponse
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.UserResponse
import com.samdoreee.fieldgeolog.ui.adapter.CommunityAdapter
import com.samdoreee.fieldgeolog.ui.adapter.MyRecordAdapter
import de.hdodenhof.circleimageview.CircleImageView
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
//                val response: Response<UserResponse> = withContext(Dispatchers.IO) {
//                    GeoApi.retrofitService.getUser(myId)
//                }
//                if (response.isSuccessful) {
//                    val userResponse: UserResponse? =
//                        response.body() // 성공한 경우 UserResponse를 추출
//
//                    if (userResponse != null) {
//                        // userResponse를 사용하여 필요한 작업 수행
//                        Log.d(Constants.TAG, "사람출력 : $userResponse")
//
//                        val nicknameTextView: TextView = findViewById(R.id.my_id)
//                        val my_profileView: CircleImageView = findViewById(R.id.my_profile)
//                        val profile_date = findViewById<TextView>(R.id.date)
//                        nicknameTextView.text = userResponse.nickName
//                        profile_date.text = "2023-11-03"
//
//                        Glide.with(this@CommunityActivity) // 현재의 Context 또는 Activity
//                            .load(userResponse.profileImage)
//                            .into(my_profileView)
//                    }
//                } else {
//                    // 요청이 실패했을 때의 처리
//                    val errorBody = response.errorBody()?.string()
//                    Log.d(Constants.TAG, "오류ㅜ : $errorBody")
//                    // 에러 메시지 등을 처리
//                }

                val response2: Response<List<ArticleResponse>> = withContext(Dispatchers.IO) {
                    GeoApi.retrofitService.getAllArticles()
                }

                if (response2.isSuccessful) {
                    val articleResponse: List<ArticleResponse>? = response2.body()?.filter { it.userId != myId }

                    Log.d(Constants.TAG, "남의 글. : ${response2.body()}")
                    if (articleResponse != null) {
                        Log.d(Constants.TAG, "남의 글 널아님. : $articleResponse")

                        val communityModels: MutableList<CommunityModel> = articleResponse.map { it.convertToCommunityModel() }.toMutableList()
                        val communitylistadapter = CommunityAdapter(communityModels, this@CommunityActivity, myId)

                        val communitylist = findViewById<RecyclerView>(R.id.communitylistview)
                        communitylist.layoutManager = LinearLayoutManager(this@CommunityActivity)
                        communitylist.adapter = communitylistadapter

//                        communitylist.setOnItemClickListener { parent, view, position, id ->
//                            val intent = Intent(this@CommunityActivity, OneRecordActivity::class.java)
//                            intent.putExtra("myId", myId)
//                            startActivity(intent)
//                        }

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

        val back_to_home_btn = findViewById<ImageButton>(R.id.back_to_home_btn)
        back_to_home_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}