package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.samdoreee.fieldgeolog.R

class NewRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_record)
        val gotohome = findViewById<ImageButton>(R.id.back_to_home_btn)
        gotohome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val floatingbtn = findViewById<FloatingActionButton>(R.id.add_new_spot_btn)
        floatingbtn.setOnClickListener {
            val intent = Intent(this, RecordWriteActivity::class.java)
            startActivity(intent)
        }
        val mapbtn = findViewById<FloatingActionButton>(R.id.on_geomap_btn)
        mapbtn.setOnClickListener {
            val intent = Intent(this, GeoMapActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "지질도 모드로 전환합니다", Toast.LENGTH_SHORT).show()
        }


        val btn = findViewById<FloatingActionButton>(R.id.current_position)
        btn.setOnClickListener{
            Toast.makeText(this, "GPS기록을 시작합니다",Toast.LENGTH_SHORT).show()
        }
    }
}
