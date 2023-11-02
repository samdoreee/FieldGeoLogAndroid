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

class CommunitySpotListRvAdapter(val context: Context, val List: List<OneRecordModel>, val myId: Long, val articleId: Long) : RecyclerView.Adapter<CommunitySpotListRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunitySpotListRvAdapter.Holder {

        Log.d(Constants.TAG, "뷰홀더는 생겼다")
        val view = LayoutInflater.from(context).inflate(R.layout.onespot_item, parent, false)
        return Holder(view)
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(List[position], position, context, myId, articleId)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, OneArticleDetailActivity::class.java)
            intent.putExtra("myId", myId)
            intent.putExtra("articleId", articleId)
            intent.putExtra("spotId", List[position].spotnum)
            Log.d(Constants.TAG, "클릭이 되었다")

            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }    }
    override fun getItemCount(): Int {
        return List.size
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val spotnum = itemView?.findViewById<TextView>(R.id.spotnum)
        fun bind(onerecordmodel : OneRecordModel, position: Int, context: Context, myId: Long, recordId: Long) {
            spotnum?.text = (position+1).toString()
            Log.d(Constants.TAG, "바인드 됨")
        }
    }

}