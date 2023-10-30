package com.samdoreee.fieldgeolog.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.network.Memo
import com.samdoreee.fieldgeolog.network.Spot

class DetailRvAdapter(val context: Context, val spot: Spot, val memoList: ArrayList<Memo>) :
    RecyclerView.Adapter<DetailRvAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_write_memo, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(memoList[position], spot, context)
    }

    override fun getItemCount(): Int {
        return memoList.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val description = itemView?.findViewById<TextView>(R.id.memo)
        val project_title = itemView?.findViewById<TextView>(R.id.title)
        val date = itemView?.findViewById<TextView>(R.id.date)
        val long = itemView?.findViewById<TextView>(R.id.location)
        val lati = itemView?.findViewById<TextView>(R.id.location)
        val weatherinfo = itemView?.findViewById<TextView>(R.id.weather)
        val strike = itemView?.findViewById<TextView>(R.id.strike)
        val dip = itemView?.findViewById<TextView>(R.id.dip)
        val rocktype = itemView?.findViewById<TextView>(R.id.rocktype)
        val geostructure = itemView?.findViewById<TextView>(R.id.geo_struct)

        fun bind(memo:Memo, spot: Spot, context: Context) {
            /* text 데이터들 binding*/
            description?.text = memo.description
            project_title?.text = spot.id.toString()
            date?.text = spot.createDT
            long?.text = spot.longitude.toString()
            lati?.text = spot.latitude.toString()
            weatherinfo?.text = spot.weatherInfo
            strike?.text = spot.strike.toString()
            dip?.text = spot.dip.toString()
            rocktype?.text = spot.rockType
            geostructure?.text = spot.geoStructure

        }

    }
}