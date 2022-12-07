package com.samdoreee.fieldgeolog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.record.Project
import com.samdoreee.fieldgeolog.record.Record

class WriteRvAdapter(val context: Context, val recordList: ArrayList<Record>): RecyclerView.Adapter<WriteRvAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_write_memo, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(recordList[position], context)
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val thumbnail = itemView?.findViewById<ImageView>(R.id.thumbnail)
        val title = itemView?.findViewById<TextView>(R.id.title)
        val location = itemView?.findViewById<TextView>(R.id.location)

        fun bind(record: Record, context: Context) {
            /*우선 이미지 썸네일을 setImageResource에 넣어줄 것인데 이미지 id를 파일명(string)으로 찾거나 또는 기본 이미지로 설정*/
            if(record.project_thumbnail != "") {
                val resourceId = context.resources.getIdentifier(record.project_thumbnail, "drawable", context.packageName)
                thumbnail?.setImageResource(resourceId)
            } else  {
                thumbnail?.setImageResource(R.mipmap.samdoree_icon)
            }
            /*text 데이터들 binding*/
            title?.text = record.project_title
            location?.text = record.project_location
        }
    }
}