package com.samdoreee.fieldgeolog.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.S3FileDownloader
import com.samdoreee.fieldgeolog.data.model.CommunityModel
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.databinding.ActivityCommunityListItemBinding
import com.samdoreee.fieldgeolog.ui.activity.DetailActivity
import com.samdoreee.fieldgeolog.ui.activity.OneArticleActivity
import java.io.File

class CommunityAdapter(private val list: List<CommunityModel>, val context:Context, val myId:Long) :
    RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val binding = ActivityCommunityListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, context, myId)
    }

    override fun getItemCount(): Int = list.size

    class CommunityViewHolder(private val binding: ActivityCommunityListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(communityModel: CommunityModel, context: Context, myId:Long) {
            binding.title.text = communityModel.title
            binding.date.text = communityModel.date.take(10)
            binding.location.text = communityModel.location
            binding.author.text = communityModel.author
            val s3FileDownloader = S3FileDownloader(context)
            val temp_filename = "0dde490d-6d4a-4333-9be2-049a9dab58ef_Screenshot_20230930-173737_My Files.jpg" // 나중에 실제 파일명으로 교체해야 함

            // S3에서 이미지 다운로드 및 화면에 표시
            s3FileDownloader.downloadFile(temp_filename, object : S3FileDownloader.DownloadListener {
                override fun onDownloadCompleted(file: File?) {
                    Log.d(Constants.TAG, "파일 다운 성공 여기, $file")
                    Glide.with(context)
                        .load(file)
                        .placeholder(R.drawable.circle_logo)
                        .error(R.drawable.logo)
                        .into(binding.thumbnail)
                }

                override fun onDownloadFailed() {
                    // 필요한 경우 에러 이미지 표시
                    Log.d(Constants.TAG, "파일 다운 실패")
                }
            })
            binding.root.setOnClickListener(){
                val intent = Intent(binding.root.context, OneArticleActivity::class.java)
                intent.putExtra(    "myId", myId)
                intent.putExtra("articleId", communityModel.id)
                ContextCompat.startActivity(binding.root.context, intent, null)


            }
        }
    }
}
