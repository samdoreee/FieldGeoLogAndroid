package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import com.samdoreee.fieldgeolog.ui.adapter.CommunityAdapter

class CommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)
        val data = mutableListOf<MyRecordModel>()
        data.add(MyRecordModel(1,"03.27.청주 기반 화강암 조사", "풍혜림", "청주시 개신동", R.drawable.geo1))
        data.add(MyRecordModel(1,"03.30.괴산 일대 조사", "풍혜림", "청주시 개신동", R.drawable.geo2))
        data.add(MyRecordModel(1,"04.21.금강 일대 조사", "풍혜림", "청주시 개신동", R.drawable.geo3))
        data.add(MyRecordModel(1,"05.01.옥천 누층군 조사", "풍혜림", "청주시 개신동", R.drawable.geo4))
        data.add(MyRecordModel(1,"06.22.채석강 및 변산반도 조사_1", "풍혜림", "청주시 개신동", R.drawable.geo5))
        data.add(MyRecordModel(1,"06.24.채석강 및 변산반도 조사_2", "풍혜림", "청주시 개신동", R.drawable.geo6))
        data.add(MyRecordModel(1,"06.26.채석강 및 변산반도 조사_3", "풍혜림", "청주시 개신동", R.drawable.geo7))
        data.add(MyRecordModel(1,"07.01.지진 위험 지역 조사", "풍혜림", "청주시 개신동", R.drawable.geo8))

        val communitylistadapter = CommunityAdapter(data)
        val communitylist = findViewById<ListView>(R.id.communitylistview)
        communitylist.adapter = communitylistadapter

        communitylist.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, OneArticleActivity::class.java)
            startActivity(intent)
        }
    }
}