package com.samdoreee.fieldgeolog.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import com.samdoreee.fieldgeolog.ui.activity.DetailActivity

class MainMyRecordRVAdapter(val context: Context, val List: List<MyRecordModel>) : RecyclerView.Adapter<MainMyRecordRVAdapter.Holder>() {

    /*화면 최초 로딩시 만들어진 view가 없을 경우 .xml을 inflate시켜서 viewholder 생성*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_main_my_record_list_item, parent, false)
        return Holder(view)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = List[position]
        holder.bind(data, context)
        /*Glide.with(context)
            .load(data.thumbnail)
            .placeholder(R.drawable.circle_logo)
            .error(R.drawable.logo)
            .into(holder.thumbnail!!)*/
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }

    }
    override fun getItemCount(): Int {
        return List.size
    }
    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val title = itemView?.findViewById<TextView>(R.id.title)
        val location = itemView?.findViewById<TextView>(R.id.location)
        val date = itemView?.findViewById<TextView>(R.id.date)
        val thumbnail = itemView?.findViewById<ImageView>(R.id.thumbnail)
        fun bind(mainmyrecord:MyRecordModel, context: Context) {
            /*text 데이터들 binding*/
            title?.text = mainmyrecord.title
            location?.text = mainmyrecord.location
            date?.text = mainmyrecord.date
            thumbnail?.setImageResource(mainmyrecord.thumbnail)
        }
    }
}