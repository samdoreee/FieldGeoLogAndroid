package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.samdoreee.fieldgeolog.R

class RecordWriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        val btnsave = findViewById<Button>(R.id.btn_save)
        btnsave.setOnClickListener {
            val intent = Intent(this, NewRecordActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "기록을 저장했습니다.", Toast.LENGTH_LONG).show()
        }
    }
}