package com.samdoreee.fieldgeolog.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.S3FileDownloader
import com.samdoreee.fieldgeolog.data.model.CommunityModel
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import com.samdoreee.fieldgeolog.databinding.ActivityMainBinding
import com.samdoreee.fieldgeolog.network.ArticleResponse
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.PersonalRecordResponse
import com.samdoreee.fieldgeolog.network.UserRequest
import com.samdoreee.fieldgeolog.network.UserResponse
import com.samdoreee.fieldgeolog.ui.adapter.MainCommunityAdapter
import com.samdoreee.fieldgeolog.ui.adapter.MainMyRecordRVAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    // 코루틴 스코프 정의
    private val scope = CoroutineScope(Dispatchers.Main)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var myId :Long = 0L
      



        //프로필 설정
        binding.profile.setImageResource(R.drawable.profile)


        binding.gotoMyrecordBtn.setOnClickListener {
            val intent = Intent(this, MyRecordActivity::class.java)
            intent.putExtra("myId", myId)
            startActivity(intent)
        }
        binding.gotoCommunityBtn.setOnClickListener {
            val intent = Intent(this, CommunityActivity::class.java)
            intent.putExtra("myId", myId)
            startActivity(intent)
        }
        binding.gotoNewrecordBtn.setOnClickListener {
            val intent = Intent(this, NewRecordActivity::class.java)
            intent.putExtra("myId", myId)
            startActivity(intent)
        }

        // 로그에 로그인 정보 데이터 출력
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(Constants.TAG, "사용자 정보 요청 실패 $error")
            } else if (user != null) {
                Log.d(Constants.TAG, "사용자 정보 요청 성공 : $user")

                val user = UserRequest(
                    id = user.id ?: 0L,
                    email = user.kakaoAccount?.email.toString(),
                    nickName =  user.kakaoAccount?.profile?.nickname.toString(),
                    profileImage = user.kakaoAccount?.profile?.thumbnailImageUrl.toString()
                )

                scope.launch {
                    try {
                        val isExists: Boolean = withContext(Dispatchers.IO) {
                            GeoApi.retrofitService.existsByUserId(user.id)
                        }
                        val response: Response<UserResponse> = withContext(Dispatchers.IO) {
                            if (isExists) {
                                GeoApi.retrofitService.getUser(user.id)
                            } else {
                                GeoApi.retrofitService.addUser(user)
                            }
                        }

                        if (response.isSuccessful) {
                            val userResponse: UserResponse? =
                                response.body() // 성공한 경우 UserResponse를 추출

                            if (userResponse != null) {
                                // userResponse를 사용하여 필요한 작업 수행
                                Log.d(Constants.TAG, "드디어ㅜㅜ : $userResponse")

                                myId = userResponse.id
                                val helloTextView: TextView = findViewById(R.id.hello_text)
                                helloTextView.text = "안녕하세요👋 " + userResponse.nickName + "님"

                                Glide.with(this@MainActivity) // 현재의 Context 또는 Activity
                                    .load(user.profileImage)
                                    .into(binding.profile)
                            }
                        } else {
                            // 요청이 실패했을 때의 처리
                            val errorBody = response.errorBody()?.string()
                            Log.d(Constants.TAG, "오류ㅜ : $errorBody")
                            // 에러 메시지 등을 처리
                        }

                        val response2: Response<List<PersonalRecordResponse>> = withContext(Dispatchers.IO) {
                            GeoApi.retrofitService.getRecordsByUserId(myId)
                        }
                        Log.d(Constants.TAG, "여기는 : $response2")
                        if (response2.isSuccessful) {
                            val personalRecordResponse: List<PersonalRecordResponse>? = response2.body()

                            if (personalRecordResponse != null) {
                                Log.d(Constants.TAG, "드디어 : $personalRecordResponse")


                                val fdata: MutableList<MyRecordModel> = personalRecordResponse.map { it.convertToMyRecordModel() }.toMutableList()
                                val myrecordlistadapter = MainMyRecordRVAdapter(this@MainActivity, fdata)
                                val myrecordlist = findViewById<RecyclerView>(R.id.RV1)
                                myrecordlist.adapter = myrecordlistadapter

                            }
                        } else {
                            // 요청이 실패했을 때의 처리
                            val errorBody = response2.errorBody()?.string()
                            Log.d(Constants.TAG, "오류ㅜ : $errorBody")
                            // 에러 메시지 등을 처리
                        }


                        val response3: Response<List<ArticleResponse>> = withContext(Dispatchers.IO) {
                            GeoApi.retrofitService.getAllArticles()
                        }
                        Log.d(Constants.TAG, "아티클목록 : $response3")
                        if (response3.isSuccessful) {
                            val articleResponse: List<ArticleResponse>? = response3.body()

                            if (articleResponse != null) {
                                Log.d(Constants.TAG, "아티클 잘받아와짐 : $articleResponse")


                                val fdata3: MutableList<CommunityModel> = articleResponse.map { it.convertToCommunityModel() }.toMutableList()
                                val communitylistadapter = MainCommunityAdapter(this@MainActivity, myId, fdata3)
                                val communitylist = findViewById<RecyclerView>(R.id.RV2)
                                communitylist.adapter = communitylistadapter

                            }
                        } else {
                            // 요청이 실패했을 때의 처리
                            val errorBody = response3.errorBody()?.string()
                            Log.d(Constants.TAG, "오류ㅜ : $errorBody")
                            // 에러 메시지 등을 처리
                        }



                    } catch (e: Exception) {
                        // 예외 처리
                        Log.e(Constants.TAG, "Error during network call", e)
                    }

                    val downloader = S3FileDownloader(context = this@MainActivity)
                    downloader.downloadFile("0dde490d-6d4a-4333-9be2-049a9dab58ef_Screenshot_20230930-173737_My Files.jpg", object : S3FileDownloader.DownloadListener {
                        override fun onDownloadCompleted(file: java.io.File?){
                            Log.d(Constants.TAG, "파일 메인에 등장 : $file")
                        }
                    })
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()  // Activity 종료 시에 코루틴도 취소
    }
}