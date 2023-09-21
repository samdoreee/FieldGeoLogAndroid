package com.samdoreee.fieldgeolog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class OneRecordAdapter(val List:MutableList<OneRecordModel>) : BaseAdapter() {
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
            convertview = LayoutInflater.from(parent?.context).inflate(R.layout.onerecorditem, parent, false)
        }
        val num = convertview!!.findViewById<TextView>(R.id.spotnum)
        val prev = convertview!!.findViewById<TextView>(R.id.preview)

        num.text = List[position].spotnum.toString()
        prev.text = List[position].preview

        return  convertview!!
    }
}