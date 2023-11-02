package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.CommentModel
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.data.model.OneRecordModel
import com.samdoreee.fieldgeolog.network.ArticleResponse
import com.samdoreee.fieldgeolog.network.CommentResponse
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.UserResponse
import com.samdoreee.fieldgeolog.ui.adapter.CommentRvAdapter
import com.samdoreee.fieldgeolog.ui.adapter.CommunitySpotListRvAdapter
import com.samdoreee.fieldgeolog.ui.adapter.MyRecordSpotListRvAdapter
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class OneArticleActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_article)

        val myId: Long = intent.getLongExtra("myId", 0L)  // 0L은 기본값
        val articleId: Long = intent.getLongExtra("articleId", 0L)  // 0L은 기본값
        val back_to_home_btn = findViewById<ImageButton>(R.id.back_to_home_btn)
        back_to_home_btn.setOnClickListener {
            val intent = Intent(this, CommunityActivity::class.java)
            intent.putExtra("myId", myId)
            startActivity(intent)
        }



        launch {
            try {


                val response: Response<UserResponse> = withContext(Dispatchers.IO) {

                    GeoApi.retrofitService.getUser(myId)

                }

                if (response.isSuccessful) {
                    val userResponse: UserResponse? =
                        response.body() // 성공한 경우 UserResponse를 추출

                    if (userResponse != null) {
                        // userResponse를 사용하여 필요한 작업 수행
                        Log.d(Constants.TAG, "사람출력 : $userResponse")


                        val my_profileView: CircleImageView = findViewById(R.id.my_profile)
                        val profile_date = findViewById<TextView>(R.id.date)
                        val nicknameTextView: TextView = findViewById(R.id.my_id)
                        nicknameTextView.text = userResponse.nickName
                        profile_date.text = "2023-11-03"

                        Glide.with(this@OneArticleActivity) // 현재의 Context 또는 Activity
                            .load(userResponse.profileImage)
                            .into(my_profileView)
                    }
                } else {
                    // 요청이 실패했을 때의 처리
                    val errorBody = response.errorBody()?.string()
                    Log.d(Constants.TAG, "오류ㅜ : $errorBody")
                    // 에러 메시지 등을 처리
                }

                val response2: Response<ArticleResponse> = withContext(Dispatchers.IO) {
                    GeoApi.retrofitService.getArticle(articleId = articleId)
                }

                if (response2.isSuccessful) {
                    val articleResponse: ArticleResponse? = response2.body()

                    Log.d(Constants.TAG, "내기록/ : ${response2.body()}")
                    if (articleResponse != null) {

                        val article_title = findViewById<TextView>(R.id.article_title)
                        val article_location = findViewById<TextView>(R.id.article_location)
                        val article_date = findViewById<TextView>(R.id.article_date)
                        val article_nickname = findViewById<TextView>(R.id.author)
                        val weather = findViewById<TextView>(R.id.weather)

                        article_title.text = articleResponse.title
                        article_location.text = "(" + articleResponse.personalRecordResponseDTO.spotResponseDTOList[0].latitude.toString()+ ", " + articleResponse.personalRecordResponseDTO.spotResponseDTOList[0].longitude + ")"
                        article_date.text = articleResponse.createDT.take(10)
                        article_nickname.text = articleResponse.nickname

                        weather.text = articleResponse.personalRecordResponseDTO.spotResponseDTOList[0].weatherInfo


                        Log.d(
                            Constants.TAG,
                            "스팟수 : ${articleResponse.personalRecordResponseDTO.spotResponseDTOList.count()}"
                        )


                        val num_preview = mutableListOf<OneRecordModel>()
                        for (i in 1..articleResponse.personalRecordResponseDTO.spotResponseDTOList.count()) {
                            num_preview.add(OneRecordModel(articleResponse.personalRecordResponseDTO.spotResponseDTOList[i-1].id))
                        }

                        /*spot num list-중앙 RV 어댑터 연결*/
                        val spotlistadapter =
                            CommunitySpotListRvAdapter(this@OneArticleActivity, num_preview, myId, articleResponse.id)
                        val spotlist = findViewById<RecyclerView>(R.id.spotlist)
                        spotlist.adapter = spotlistadapter

                    } else {
                        // 요청이 실패했을 때의 처리
                        val errorBody = response2.errorBody()?.string()
                        Log.d(Constants.TAG, "오류ㅜ : $errorBody")
                        // 에러 메시지 등을 처리
                    }


                    val response3: Response<List<CommentResponse>> = withContext(Dispatchers.IO) {
                        GeoApi.retrofitService.getAllComments(articleId = articleId)
                    }

                    if (response3.isSuccessful) {
                        val commentResponse: List<CommentResponse>? = response3.body()

                        Log.d(Constants.TAG, "내댓글창 : ${response3.body()}")
                        if (commentResponse != null) {

                            //여기써야됨
                            val comment_data: MutableList<CommentModel> = commentResponse.map { it.convertToCommentModel() }.toMutableList()
                            val commentlistadapter =
                                CommentRvAdapter(this@OneArticleActivity, comment_data)
                            val commentList = findViewById<RecyclerView>(R.id.commentlistview)
                            commentList.layoutManager = LinearLayoutManager(this@OneArticleActivity)
                            commentList.adapter = commentlistadapter

                        } else {
                            // 요청이 실패했을 때의 처리
                            val errorBody = response3.errorBody()?.string()
                            Log.d(Constants.TAG, "오류ㅜ : $errorBody")
                            // 에러 메시지 등을 처리
                        }
                    }
//                    var comment_data = mutableListOf<CommentModel>()
//                    comment_data.add(
//                        CommentModel(
//                            1, "댓글 내용은 다음과 같습니다\n언니예뻐요! 정현언니! 앙앙",
//                            "2022.11.22", "풍혜림", R.drawable.profile22
//                        )
//                    )
//                    comment_data.add(
//                        CommentModel(
//                            2, "댓글 내용은 다음과 같습니다 진영 더바보\n언니예뻐요! 정현언니! 앙앙",
//                            "2022.11.22", "풍혜림", R.drawable.profile22
//                        )
//                    )
//                    comment_data.add(
//                        CommentModel(
//                            3, "댓글 내용은 다음과 같습니다\n언니예뻐요! 정현언니! 앙앙",
//                            "2022.11.22", "풍혜림", R.drawable.profile22
//                        )
//                    )
//                    comment_data.add(
//                        CommentModel(
//                            4, "댓글 내용은 다음과 같습니다 정현바보\n언니예뻐요! 정현언니! 앙앙",
//                            "2022.11.22", "풍혜림", R.drawable.profile22
//                        )
//                    )
//                    comment_data.add(
//                        CommentModel(
//                            5, "댓글 내용은 다음과 같습니다\n 메롱메롱 언니예뻐요! 정현언니! 앙앙",
//                            "2022.11.22", "풍혜림", R.drawable.profile22
//                        )
//                    )
//                    /*comment list-하위👋 RV 어댑터 연결*/
//                    val commentlistadapter =
//                        CommentRvAdapter(this@OneArticleActivity, comment_data)
//                    val commentList = findViewById<RecyclerView>(R.id.commentlistview)
//                    commentList.layoutManager = LinearLayoutManager(this@OneArticleActivity)
//                    commentList.adapter = commentlistadapter


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