package com.samdoreee.fieldgeolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast

class MyRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_record)

        val data = mutableListOf<MyRecordModel>()
        data.add(MyRecordModel(1,"03.27.청주 기반 화강암 조사", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(2,"03.30.괴산 일대 조사", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(3,"04.21.금강 일대 조사", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(4,"05.01.옥천 누층군 조사", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(5,"06.22.채석강 및 변산반도 조사_1", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(6,"06.24.채석강 및 변산반도 조사_2", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(7,"06.26.채석강 및 변산반도 조사_3", "풍혜림", "청주시 개신동"))
        data.add(MyRecordModel(8,"07.01.지진 위험 지역 조사", "풍혜림", "청주시 개신동"))

        val myrecordlistadapter = MyRecordAdapter(data)
        val myrecordlist = findViewById<ListView>(R.id.myrecordlistview)
        myrecordlist.adapter = myrecordlistadapter

        myrecordlist.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, OneRecordActivity::class.java)
            startActivity(intent)
        }
    }
}