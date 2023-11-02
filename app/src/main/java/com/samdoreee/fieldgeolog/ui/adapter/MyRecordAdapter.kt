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
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.S3FileDownloader
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.ui.activity.OneRecordActivity
import java.io.File

class MyRecordAdapter(val dataList: MutableList<MyRecordModel>, val context: Context, val myId: Long) : RecyclerView.Adapter<MyRecordAdapter.MyRecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_myrecord_list_item, parent, false)
        return MyRecordViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyRecordViewHolder, position: Int) {
        holder.bind(dataList[position], context, myId)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, OneRecordActivity::class.java)
            intent.putExtra("myId", myId)
            intent.putExtra("recordId", dataList[position].id)
            context.startActivity(intent) // ContextCompat 없이 context 사용
        }


    }

    inner class MyRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val date: TextView = itemView.findViewById(R.id.date)
        val location: TextView = itemView.findViewById(R.id.location)
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        val s3FileDownloader = S3FileDownloader(context)



        fun bind(data: MyRecordModel, context: Context, myId: Long) {
            title.text = data.title
            date.text = data.date.take(10)
            location.text = data.location
            val temp_filename = "0dde490d-6d4a-4333-9be2-049a9dab58ef_Screenshot_20230930-173737_My Files.jpg" // 나중에 실제 파일명으로 교체해야 함

            // S3에서 이미지 다운로드 및 화면에 표시
            s3FileDownloader.downloadFile(temp_filename, object : S3FileDownloader.DownloadListener {
                override fun onDownloadCompleted(file: File?) {
                    Log.d(Constants.TAG, "파일 다운 성공 여기, $file")
                    Glide.with(context)
                        .load(file)
                        .placeholder(R.drawable.circle_logo)
                        .error(R.drawable.logo)
                        .into(thumbnail)
                }

                override fun onDownloadFailed() {
                    // 필요한 경우 에러 이미지 표시
                    Log.d(Constants.TAG, "파일 다운 실패")
                }
            })


        }
    }
}
