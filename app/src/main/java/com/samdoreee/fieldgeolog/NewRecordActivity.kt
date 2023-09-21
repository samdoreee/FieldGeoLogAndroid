package com.samdoreee.fieldgeolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_record)

        val floatingbtn = findViewById<FloatingActionButton>(R.id.btn_to_writeactivity)
        floatingbtn.setOnClickListener {
            val intent = Intent(this, RecordWriteActivity::class.java)
            startActivity(intent)
        }
        val mapbtn = findViewById<FloatingActionButton>(R.id.btn_to_geoMap)
        mapbtn.setOnClickListener {
            val intent = Intent(this, GeoMapActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "지질도 모드로 전환합니다", Toast.LENGTH_SHORT).show()
        }

        val gotolist = findViewById<Button>(R.id.btn_record_start)
        gotolist.setOnClickListener {
            val intent = Intent(this, MyRecordActivity::class.java)
            startActivity(intent)
        }
        val btn = findViewById<Button>(R.id.btn_start)
        btn.setOnClickListener{
            Toast.makeText(this, "GPS기록을 시작합니다",Toast.LENGTH_SHORT).show()
        }
    }
}
