package com.samdoreee.fieldgeolog.ui.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.ui.adapter.ProjectRvAdapter
import kotlinx.coroutines.runBlocking

class ProjectListActivity : AppCompatActivity() {

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