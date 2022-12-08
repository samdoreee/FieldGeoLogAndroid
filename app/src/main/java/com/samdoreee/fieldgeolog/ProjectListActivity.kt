package com.samdoreee.fieldgeolog


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.record.Project
import com.samdoreee.fieldgeolog.record.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.logging.Logger

class ProjectListActivity : AppCompatActivity() {
    var projectList = TempMemory.tempprojectmemory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)

        val projectListActivity = this

        runBlocking {
            val allSpots = GeoApi.retrofitService.getAllSpots()

            val projectadapter = ProjectRvAdapter(projectListActivity, allSpots)
            val projectrecyclerview = findViewById<RecyclerView>(R.id.projectRecyclerView)
            projectrecyclerview.adapter = projectadapter

            val layoutmanager = LinearLayoutManager(projectListActivity)
            projectrecyclerview.layoutManager = layoutmanager
            projectrecyclerview.setHasFixedSize(true)
        }
    }
}