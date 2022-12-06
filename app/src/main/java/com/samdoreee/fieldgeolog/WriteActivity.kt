package com.samdoreee.fieldgeolog

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.samdoreee.fieldgeolog.record.Record


class WriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

    }

    private fun onClickBtnSave() {
        val intent = Intent(this, ProjectListActivity::class.java)
        startActivity(intent)
    }




}