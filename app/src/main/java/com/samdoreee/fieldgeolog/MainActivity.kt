package com.samdoreee.fieldgeolog

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.databinding.ActivityMain2Binding
import com.samdoreee.fieldgeolog.network.GeoApi
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*      val projectListActivity = this

        runBlocking {
            val allSpots = GeoApi.retrofitService.getAllSpots()

            val projectadapter = ProjectRvAdapter(projectListActivity, allSpots)
            val projectrecyclerview = findViewById<RecyclerView>(R.id.projectRecyclerView)
            projectrecyclerview.adapter = projectadapter

            val layoutmanager = LinearLayoutManager(projectListActivity)
            projectrecyclerview.layoutManager = layoutmanager
            projectrecyclerview.setHasFixedSize(true)
        }

        //프로필 설정
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_2)
        binding.profile.setImageResource(R.drawable.photo1)

        //fake data로 recyclerview 테스트
        val data =  mutableListOf<MyRecordModel>()
        data.add(MyRecordModel(1,"03.27.청주 기반 화강암 조사", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(2,"03.30.괴산 일대 조사", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(3,"04.21.금강 일대 조사", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(4,"05.01.옥천 누층군 조사", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(5,"06.22.채석강 및 변산반도 조사_1", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(6,"06.24.채석강 및 변산반도 조사_2", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(7,"06.26.채석강 및 변산반도 조사_3", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(8,"07.01.지진 위험 지역 조사", "풍혜림", "청주시 개신동"))

        val main_myrecord_adapter = MyRecordAdapter(data)
        val main_myrecord_recyclerview = binding.RV1
        main_myrecord_recyclerview.adapter = main_myrecord_adapter

        val myrecord_layoutmanager = LinearLayoutManager(this)
        main_myrecord_recyclerview.layoutManager = myrecord_layoutmanager

        binding.gotoMyrecordBtn.setOnClickListener {
            val intent = Intent(this, MyRecordActivity::class.java)
            startActivity(intent)
        }
        main_myrecord_recyclerview.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, OneRecordActivity::class.java)
            startActivity(intent)
        }
*/

        /*binding.myrecordbtn.setOnClickListener {
            val intent = Intent(this, MyRecordActivity::class.java)
            startActivity(intent)
        }
        binding.communitybtn.setOnClickListener {
            val intent = Intent(this, CommunityActivity::class.java)
            startActivity(intent)
        }
        binding.newrecordbtn.setOnClickListener {
            val intent = Intent(this, NewRecordActivity::class.java)
            startActivity(intent)
        }
        binding.mypagebtn.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        // 로그에 로그인 정보 데이터 출력
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(Constants.TAG, "사용자 정보 요청 실패 $error")
            } else if (user != null) {
                Log.d(Constants.TAG, "사용자 정보 요청 성공 : $user")


            // 아래 코드처럼 데이터 활용하면 됨
            // binding.txtNickName.text = user.kakaoAccount?.profile?.nickname

            }
        }*/



    }
}