package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.OneRecordModel
import com.samdoreee.fieldgeolog.ui.adapter.MainMyRecordRVAdapter
import com.samdoreee.fieldgeolog.ui.adapter.OneRecordAdapter
import com.samdoreee.fieldgeolog.ui.adapter.SpotListRvAdapter

class OneRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_record)

        val num_preview = mutableListOf<OneRecordModel>()
        num_preview.add(OneRecordModel(1))
        num_preview.add(OneRecordModel(2))
        num_preview.add(OneRecordModel(3))
        num_preview.add(OneRecordModel(4))
        num_preview.add(OneRecordModel(5))
        num_preview.add(OneRecordModel(6))
        num_preview.add(OneRecordModel(7))




        val oneRecordListView = findViewById<ListView>(R.id.spotlist)
        val oneRecordListViewAdapater = OneRecordAdapter(num_preview)
        oneRecordListView.adapter = oneRecordListViewAdapater

        oneRecordListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ReadPersonalRecordActivity::class.java)
            startActivity(intent)
        }
    }
}