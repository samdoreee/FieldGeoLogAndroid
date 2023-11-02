package com.samdoreee.fieldgeolog.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.data.model.OneRecordModel
import com.samdoreee.fieldgeolog.ui.activity.OneArticleDetailActivity
import com.samdoreee.fieldgeolog.ui.activity.OneRecordDetailActivity

class MyRecordSpotListRvAdapter (val context: Context, val List: List<OneRecordModel>, val myId: Long, val recordId: Long) : RecyclerView.Adapter<MyRecordSpotListRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecordSpotListRvAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.onespot_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(List[position], context, position, myId, recordId)
        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView?.context, OneRecordDetailActivity::class.java)
            intent.putExtra("spotId", List[position].spotnum)
            Log.d(Constants.TAG, "position : $position , ${List[position].spotnum}")
            intent.putExtra("myId", myId)
            intent.putExtra("recordId", recordId)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }
    override fun getItemCount(): Int {
        return List.size
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val spotnum = itemView?.findViewById<TextView>(R.id.spotnum)
        fun bind(onerecordmodel : OneRecordModel, context: Context, num: Int, myId: Long, recordId: Long) {
            spotnum?.text = (num+1).toString()
        }
    }

}

