package com.samdoreee.fieldgeolog.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.OneRecordModel
import com.samdoreee.fieldgeolog.ui.activity.OneArticleDetailActivity

class SpotListRvAdapter(val context: Context, val List: List<OneRecordModel>) : RecyclerView.Adapter<SpotListRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotListRvAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.onespot_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(List[position], context)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, OneArticleDetailActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }    }
    override fun getItemCount(): Int {
        return List.size
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val spotnum = itemView?.findViewById<TextView>(R.id.spotnum)
        fun bind(onerecordmodel : OneRecordModel, context: Context) {
            spotnum?.text = onerecordmodel.spotnum.toString()
        }
    }

}