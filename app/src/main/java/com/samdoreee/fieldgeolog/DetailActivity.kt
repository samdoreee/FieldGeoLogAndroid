package com.samdoreee.fieldgeolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.samdoreee.fieldgeolog.databinding.ActivityDetailBinding
import com.samdoreee.fieldgeolog.databinding.ActivityWriteBinding
import com.samdoreee.fieldgeolog.network.Memo
import com.samdoreee.fieldgeolog.network.Spot

lateinit var binding: ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val btn_back_to_list = findViewById<Button>(R.id.btn_back_to_list)
        btn_back_to_list.setOnClickListener {
            startActivity(Intent(this, ProjectListActivity::class.java))
        }
    }
}