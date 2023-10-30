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
import com.samdoreee.fieldgeolog.ui.activity.DetailActivity
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.network.Spot

class ProjectRvAdapter(val context: Context, val spotList: List<Spot>) : RecyclerView.Adapter<ProjectRvAdapter.Holder>() {

    /*화면 최초 로딩시 만들어진 view가 없을 경우 .xml을 inflate시켜서 viewholder 생성*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_project_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return spotList.size
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val thumbnail = itemView?.findViewById<ImageView>(R.id.thumbnail)

        val strike = itemView?.findViewById<TextView>(R.id.strike)
        val rockType = itemView?.findViewById<TextView>(R.id.rocktype)
        val geoStructure = itemView?.findViewById<TextView>(R.id.geo_struct)
        val dip = itemView?.findViewById<TextView>(R.id.dip)

        fun bind(spot: Spot, context: Context) {
            /*우선 이미지 썸네일을 setImageResource에 넣어줄 것인데 이미지 id를 파일명(string)으로 찾거나 또는 기본 이미지로 설정*/
            thumbnail?.setImageResource(R.mipmap.samdoree_icon)
            /*text 데이터들 binding*/
            strike?.text = spot.strike.toString()
            rockType?.text = spot.rockType.toString()
            geoStructure?.text = spot.geoStructure.toString()
            dip?.text = spot.dip.toString()
        }
    }
}