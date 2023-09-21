package com.samdoreee.fieldgeolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.samdoreee.fieldgeolog.databinding.ActivityRockDicBinding

class RockDicActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRockDicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rock_dic)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rock_dic)

        binding.aplite.setOnClickListener {
            val intent = Intent(this, RockDicDetailActivity::class.java)
            startActivity(intent)
        }


    }
}