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
import com.samdoreee.fieldgeolog.data.model.CommentModel
import com.samdoreee.fieldgeolog.data.model.OneRecordModel
import com.samdoreee.fieldgeolog.ui.activity.OneArticleDetailActivity
import org.w3c.dom.Comment


class CommentRvAdapter(val context: Context, val List: List<CommentModel>) : RecyclerView.Adapter<CommentRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentRvAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false)
        return Holder(view)    }

    override fun onBindViewHolder(holder: CommentRvAdapter.Holder, position: Int) {
        holder.bind(List[position], context)
    }

    override fun getItemCount(): Int {
        return List.size
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val c_content = itemView?.findViewById<TextView>(R.id.comment_content)
        val c_author = itemView?.findViewById<TextView>(R.id.comment_author)
        val c_date = itemView?.findViewById<TextView>(R.id.comment_date)
        val c_author_profile = itemView?.findViewById<ImageView>(R.id.comment_author_profile)
        fun bind(commentmodel: CommentModel, context: Context) {
            c_content?.text = commentmodel.comment_content.toString()
            c_author?.text = commentmodel.comment_author.toString()
            c_date?.text = commentmodel.comment_date.toString()
            c_author_profile?.setImageResource(R.drawable.profile22)
        }
    }
}