package com.samdoreee.fieldgeolog.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.S3FileDownloader
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import com.samdoreee.fieldgeolog.ui.activity.DetailActivity
import java.io.File

class MainMyRecordRVAdapter(val context: Context, val List: List<MyRecordModel>) : RecyclerView.Adapter<MainMyRecordRVAdapter.Holder>() {

    /*화면 최초 로딩시 만들어진 view가 없을 경우 .xml을 inflate시켜서 viewholder 생성*/
    private val s3FileDownloader = S3FileDownloader(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_main_my_record_list_item, parent, false)
        return Holder(view)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = List[position]
        holder.bind(data, context)
//        Glide.with(context)
//            .load(data.thumbnail)
//            .placeholder(R.drawable.circle_logo)
//            .error(R.drawable.logo)
//            .into(holder.thumbnail!!)
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
        val location = itemView?.findViewById<TextView>(R.id.location)
        val date = itemView?.findViewById<TextView>(R.id.date)
        val thumbnail = itemView?.findViewById<ImageView>(R.id.thumbnail)
        fun bind(mainmyrecord:MyRecordModel, context: Context) {
            /*text 데이터들 binding*/
            title?.text = mainmyrecord.title
            location?.text = mainmyrecord.location
            date?.text = mainmyrecord.date.take(10)
            //thumbnail?.setImageResource(mainmyrecord.thumbnail)
            val tempFilename: String = "0dde490d-6d4a-4333-9be2-049a9dab58ef_Screenshot_20230930-173737_My Files.jpg" // 나중에 실제 파일명으로 교체해야 함
            s3FileDownloader.downloadFile(tempFilename, object : S3FileDownloader.DownloadListener {
                override fun onDownloadCompleted(file: File?) {
                    Log.d(Constants.TAG, "메인 파일 다운 성공")
                    // 3. 이미지를 thumbnail ImageView에 로딩
                    Glide.with(context)
                        .load(file)
                        .error(R.drawable.logo)
                        .into(thumbnail!!)
                }

                override fun onDownloadFailed() {
                    Log.d(Constants.TAG, "메인 파일 다운 실패")
                    Glide.with(context)
                        .load(R.drawable.logo) // 혹은 다른 에러 이미지
                        .into(thumbnail!!)
                }
            })
        }
    }
}