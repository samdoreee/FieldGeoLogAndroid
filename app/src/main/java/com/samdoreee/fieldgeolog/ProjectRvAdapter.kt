package com.samdoreee.fieldgeolog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.record.Project

class ProjectRvAdapter(val context: Context, val projectList: ArrayList<Project>) : RecyclerView.Adapter<ProjectRvAdapter.Holder>() {

    /*화면 최초 로딩시 만들어진 view가 없을 경우 .xml을 inflate시켜서 viewholder 생성*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_project_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
/*
        holder?.bind(projectList[position], context)
*/
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, WriteActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val thumbnail = itemView?.findViewById<ImageView>(R.id.thumbnail)
        val title = itemView?.findViewById<TextView>(R.id.title)
        val author = itemView?.findViewById<TextView>(R.id.author)
        val location = itemView?.findViewById<TextView>(R.id.location)

        fun bind(project: Project, context: Context) {
            /*우선 이미지 썸네일을 setImageResource에 넣어줄 것인데 이미지 id를 파일명(string)으로 찾거나 또는 기본 이미지로 설정*/
            if(project.photo != "") {
                val resourceId = context.resources.getIdentifier(project.photo, "drawable", context.packageName)
                thumbnail?.setImageResource(resourceId)
            } else  {
                thumbnail?.setImageResource(R.mipmap.samdoree_icon)
            }
            /*text 데이터들 binding*/
            title?.text = project.title
            author?.text = project.author
            location?.text = project.location
        }

    }
}