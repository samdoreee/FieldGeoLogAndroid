package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.OneRecordModel
import com.samdoreee.fieldgeolog.ui.adapter.OneArticleAdapter

class OneArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_article)

        val num_preview = mutableListOf<OneRecordModel>()
        num_preview.add(OneRecordModel(1, "spot1:옥천누층군 조사시작"))
        num_preview.add(OneRecordModel(2, "spot2:절리군 조사"))
        num_preview.add(OneRecordModel(3, "spot3:관입 및 접촉변성대 조사"))
        num_preview.add(OneRecordModel(4, "spot4:엽리 조사"))
        num_preview.add(OneRecordModel(5, "spot5:습곡 조사"))
        num_preview.add(OneRecordModel(6, "spot6:암종경계 조사"))
        num_preview.add(OneRecordModel(7, "spot7:변성도 조사"))

        val oneArticleListView = findViewById<ListView>(R.id.cardView)
        val oneArticleListViewAdapater = OneArticleAdapter(num_preview)
        oneArticleListView.adapter = oneArticleListViewAdapater

        oneArticleListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ReadArticleActivity::class.java)
            startActivity(intent)
        }
    }
}