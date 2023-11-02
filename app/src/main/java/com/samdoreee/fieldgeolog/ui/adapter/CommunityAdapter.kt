package com.samdoreee.fieldgeolog.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.CommunityModel

class CommunityAdapter(val List:MutableList<CommunityModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return List.size
    }

    override fun getItem(position: Int): Any {
        return List[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertview = convertView
        if (convertview == null) {
            convertview = LayoutInflater.from(parent?.context).inflate(R.layout.activity_community_list_item, parent, false)
        }
        var title = convertview!!.findViewById<TextView>(R.id.title)
        var date = convertview!!.findViewById<TextView>(R.id.date)
        var location = convertview!!.findViewById<TextView>(R.id.location)
        var author = convertview!!.findViewById<TextView>(R.id.author)
        title.text = List[position].title
        date.text = List[position].date.take(10)
        location.text = List[position].location
        author.text = List[position].author
        return  convertview!!
    }
}