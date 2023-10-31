package com.samdoreee.fieldgeolog.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.CommunityModel
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.ui.activity.DetailActivity
class MainCommunityAdapter(val context: Context, val List:MutableList<CommunityModel>) : RecyclerView.Adapter<MainCommunityAdapter.Holder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_main_community_list_item, parent, false)
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
        val author = itemView?.findViewById<TextView>(R.id.author)
        val location = itemView?.findViewById<TextView>(R.id.location)
        val date = itemView?.findViewById<TextView>(R.id.date)
        val thumbnail = itemView?.findViewById<ImageView>(R.id.thumbnail)
        fun bind(maincommunityrecord:CommunityModel, context: Context) {
            /*text 데이터들 binding*/
            title?.text = maincommunityrecord.title
            author?.text  = maincommunityrecord.author
            location?.text = maincommunityrecord.location
            date?.text = maincommunityrecord.date
            thumbnail?.setImageResource(maincommunityrecord.thumbnail)
        }
    }
}

