package com.samdoreee.fieldgeolog.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.samdoreee.fieldgeolog.data.model.OneRecordModel
import com.samdoreee.fieldgeolog.R

class OneArticleAdapter(val List:MutableList<OneRecordModel>) : BaseAdapter() {
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

        num.text = List[position].spotnum.toString()

        return  convertview!!
    }
}