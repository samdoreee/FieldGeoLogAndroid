package com.samdoreee.fieldgeolog


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.record.Project

class ProjectRecordActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_project_record)

        val projectadapter = ProjectRvAdapter(this, projectList)
        val projectrecyclerview = findViewById<RecyclerView>(R.id.projectRecyclerView)
        projectrecyclerview.adapter = projectadapter

        val layoutmanager = LinearLayoutManager(this)
        projectrecyclerview.layoutManager = layoutmanager
        projectrecyclerview.setHasFixedSize(true)
    }

    // 플로팅 버튼 클릭 시..! 새로운 기록 창으로 전환
    public fun clickBtn(view: View) {
        val intent = Intent(this, ProjectRecordActivity::class.java)
        startActivity(intent)
    }

}