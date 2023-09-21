package com.samdoreee.fieldgeolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class RecordWriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_record)

        val btnsave = findViewById<Button>(R.id.saverecord)
        btnsave.setOnClickListener {
            val intent = Intent(this, NewRecordActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "기록을 저장했습니다.", Toast.LENGTH_LONG).show()
        }
    }
}