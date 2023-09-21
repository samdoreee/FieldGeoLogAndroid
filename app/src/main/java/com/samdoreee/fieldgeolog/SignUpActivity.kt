package com.samdoreee.fieldgeolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.samdoreee.fieldgeolog.LoginActivity
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        binding.isDuplicate.setOnClickListener {
            Toast.makeText(this, "중복이 확인되었습니다.", Toast.LENGTH_LONG).show()
        }

        binding.btnSave.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}