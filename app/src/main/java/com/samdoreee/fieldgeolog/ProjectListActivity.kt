package com.samdoreee.fieldgeolog


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.record.Project
import com.samdoreee.fieldgeolog.record.Record
import java.util.logging.Logger

class ProjectListActivity : AppCompatActivity() {
    var projectList = TempMemory.tempprojectmemory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)

        val projectadapter = ProjectRvAdapter(this, projectList)
        val projectrecyclerview = findViewById<RecyclerView>(R.id.projectRecyclerView)
        projectrecyclerview.adapter = projectadapter

        val layoutmanager = LinearLayoutManager(this)
        projectrecyclerview.layoutManager = layoutmanager
        projectrecyclerview.setHasFixedSize(true)

    }
}