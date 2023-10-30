package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val btn_back_to_list = findViewById<ImageButton>(R.id.back_to_home_btn)
        btn_back_to_list.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.profile.setImageResource(R.drawable.profile)
        binding.profile2.setImageResource(R.drawable.profile22)
    }
}