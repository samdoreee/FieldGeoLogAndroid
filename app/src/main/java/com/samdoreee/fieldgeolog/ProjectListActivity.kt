package com.samdoreee.fieldgeolog


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.samdoreee.fieldgeolog.record.Project

class ProjectListActivity : AppCompatActivity() {
    var projectList = arrayListOf<Project>(
        Project("1st record", "Hyerim Pung", "Cheongju", "igneous_3434"),
        Project("2nd record", "Minju Kim", "Yongin", "seimentary_1759"),
        Project("3rd record", "Chisan Ahn", "Seoul", "seimentary_2389"),
        Project("4th record", "Jinyoung Lee", "Busan", ""),
        Project("5th record", "Minjeong Seo", "Jeonju", "seimentary_2389"),
        Project("6th record", "Seunghyeon Lee", "Daejeon", "igneous_3434")
    )

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