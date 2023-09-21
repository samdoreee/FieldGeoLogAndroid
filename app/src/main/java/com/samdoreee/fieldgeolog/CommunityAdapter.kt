package com.samdoreee.fieldgeolog

import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.samdoreee.fieldgeolog.R

class CommunityAdapter(val List:MutableList<MyRecordModel>) : BaseAdapter() {
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
            convertview = LayoutInflater.from(parent?.context).inflate(R.layout.communityitem, parent, false)
        }
        var title = convertview!!.findViewById<TextView>(R.id.title)
        var author = convertview!!.findViewById<TextView>(R.id.author)
        var location = convertview!!.findViewById<TextView>(R.id.location)
        var thumbnail = convertview!!.findViewById<ImageView>(R.id.thumbnail)
        title.text = List[position].title
        author.text = List[position].author
        location.text = List[position].location
        return  convertview!!
    }
}