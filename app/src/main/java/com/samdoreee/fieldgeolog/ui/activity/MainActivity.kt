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
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.data.model.CommunityModel
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import com.samdoreee.fieldgeolog.databinding.ActivityMainBinding
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.UserRequest
import com.samdoreee.fieldgeolog.network.UserResponse
import com.samdoreee.fieldgeolog.ui.adapter.MainCommunityAdapter
import com.samdoreee.fieldgeolog.ui.adapter.MainMyRecordRVAdapter
import kotlinx.coroutines.runBlocking
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fdata = mutableListOf<MyRecordModel>()
        fdata.add(MyRecordModel(1, "테스트1 기록입니다.","청주 기반 화강암 조사", "2022.11.22",R.drawable.geo1))
        fdata.add(MyRecordModel(2, "테스트2 기록입니다.","괴산 일대 조사", "2022.11.22", R.drawable.geo2))
        fdata.add(MyRecordModel(3, "테스트3 기록입니다.","금강 일대 조사", "2022.11.22", R.drawable.geo3))
        fdata.add(MyRecordModel(4, "테스트4 기록입니다.","옥천 누층군 조사", "2022.11.22", R.drawable.geo4))
        fdata.add(MyRecordModel(5, "테스트5 기록입니다.","채석강 및 변산반도 조사_1","2022.11.22",R.drawable.geo5))

        val fdata2 = mutableListOf<CommunityModel>()
        fdata2.add(CommunityModel(1, "테스트 기록1입니다.","풍혜림", "청주 기반 화강암 조사", "2022.11.22", R.drawable.geo6))
        fdata2.add(CommunityModel(1, "테스트 기록2입니다.","풍혜림", "괴산 일대 조사", "2022.11.22",R.drawable.geo7))
        fdata2.add(CommunityModel(1, "테스트 기록3입니다.","풍혜림", "금강 일대 조사", "2022.11.22", R.drawable.geo8))
        fdata2.add(CommunityModel(1, "테스트 기록4입니다.","풍혜림", "옥천 누층군 조사", "2022.11.22", R.drawable.geo9))
        fdata2.add(CommunityModel(1, "테스트 기록5입니다.","풍혜림", "채석강 및 변산반도 조사_1","2022.11.22", R.drawable.geo10))

        val myrecordlistadapter = MainMyRecordRVAdapter(this, fdata)
        val myrecordlist = findViewById<RecyclerView>(R.id.RV1)
        myrecordlist.adapter = myrecordlistadapter

        val communitylistadapter = MainCommunityAdapter(this, fdata2)
        val communitylist = findViewById<RecyclerView>(R.id.RV2)
        communitylist.adapter = communitylistadapter


        //프로필 설정
        binding.profile.setImageResource(R.drawable.profile)


        binding.gotoMyrecordBtn.setOnClickListener {
            val intent = Intent(this, MyRecordActivity::class.java)
            startActivity(intent)
        }
        binding.gotoCommunityBtn.setOnClickListener {
            val intent = Intent(this, CommunityActivity::class.java)
            startActivity(intent)
        }
        binding.gotoNewrecordBtn.setOnClickListener {
            val intent = Intent(this, NewRecordActivity::class.java)
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

                runBlocking {
                    try {

                        Log.d(Constants.TAG, "new~User = $user")
                        val response: Response<UserResponse> = GeoApi.retrofitService.addUser(user)

                        if (response.isSuccessful) {
                            val userResponse: UserResponse? = response.body() // 성공한 경우 UserResponse를 추출

                            if (userResponse != null) {
                                // userResponse를 사용하여 필요한 작업 수행
                                Log.d(Constants.TAG, "드디어ㅜㅜ : $userResponse")
                            }
                        } else {
                            // 요청이 실패했을 때의 처리
                            val errorBody = response.errorBody()?.string()
                            // 에러 메시지 등을 처리
                        }
                    } catch (e: Exception) {
                        // 예외 처리
                    }

                    val helloTextView: TextView = findViewById(R.id.hello_text)
                    helloTextView.text = "안녕하세요👋 "+user.nickName+"님"

                    Glide.with(this@MainActivity) // 현재의 Context 또는 Activity
                        .load(user.profileImage)
                        .into(binding.profile)
                }

            }
        }



    }
}