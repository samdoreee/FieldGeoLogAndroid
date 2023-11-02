package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.S3FileDownloader
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.network.ArticleResponse
import com.samdoreee.fieldgeolog.network.FileResponse
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.MemoResponse
import com.samdoreee.fieldgeolog.network.SpotResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.File

class OneArticleDetailActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_article_detail)
        val myId:Long = intent.getLongExtra("myId", 0L)
        val articleId:Long = intent.getLongExtra("articleId", 0L)
        var spotId:Long = intent.getLongExtra("spotId", 0L)
        val s3FileDownloader = S3FileDownloader(this)

        val back_to_home_btn = findViewById<ImageButton>(R.id.back_to_home_btn)
        Log.d(Constants.TAG, "여기쯤쯤")
        back_to_home_btn.setOnClickListener {
            val intent = Intent(this, OneArticleActivity::class.java)
            intent.putExtra("myId", myId)
            intent.putExtra("articleId", articleId)
            startActivity(intent)
        }

        launch {
            try {

                val response: Response<ArticleResponse> = withContext(Dispatchers.IO) {
                    GeoApi.retrofitService.getArticle(articleId = articleId)
                }
                Log.d(Constants.TAG, "여기는왔으려나")

                if (response.isSuccessful) {
                    val articleResponse: ArticleResponse? = response.body()

                    Log.d(Constants.TAG, "아티클의 것들/ : ${response.body()}")
                    if (articleResponse != null) {
                        val article_author = findViewById<TextView>(R.id.author)
                        val article_title = findViewById<TextView>(R.id.article_title)
                        val article_location = findViewById<TextView>(R.id.article_location)
                        val article_date = findViewById<TextView>(R.id.article_date)
                        val weather = findViewById<TextView>(R.id.weather)

                        article_author.text = articleResponse.nickname
                        article_title.text = articleResponse.title
                        article_location.text =
                            "(" + articleResponse.personalRecordResponseDTO.spotResponseDTOList[0].latitude.toString() + ", " + articleResponse.personalRecordResponseDTO.spotResponseDTOList[0].longitude + ")"
                        article_date.text = articleResponse.createDT.take(10)
                        weather.text =
                            articleResponse.personalRecordResponseDTO.spotResponseDTOList[0].weatherInfo

                        val spotlist:List<SpotResponse> = articleResponse.personalRecordResponseDTO.spotResponseDTOList
                        var now_spot: SpotResponse = spotlist[0]
                        for(i in 1..spotlist.count()){
                            if(spotlist[i-1].id == spotId){
                                now_spot = spotlist[i-1]
                                break
                            }
                        }
                        val strike = findViewById<TextView>(R.id.strike)
                        val dip = findViewById<TextView>(R.id.dip)
                        val rocktype = findViewById<TextView>(R.id.rocktype)
                        val geo_struct = findViewById<TextView>(R.id.geo_struct)
                        strike.text = now_spot.strike.toString()
                        dip.text = now_spot.dip.toString()
                        rocktype.text = now_spot.rockType
                        geo_struct.text = now_spot.geoStructure

                        val now_memo:MemoResponse = now_spot.memoResponseDTOList[0]
                        val memo = findViewById<TextView>(R.id.memo)
                        memo.text = now_memo.description

                        val now_pictures:List<FileResponse> = now_memo.pictureResponseDTOList
                        var n=3
                        if (now_pictures.count() < 3){
                            n = now_pictures.count()
                        }
                        for(i in 1..n){
                            var here_photo = findViewById<ImageView>(R.id.memo_image1)
                            if(i==2) {
                                here_photo = findViewById<ImageView>(R.id.memo_image2)
                            }
                            else if(i==3) {
                                here_photo = findViewById<ImageView>(R.id.memo_image3)
                            }
                            s3FileDownloader.downloadFile(now_pictures[i-1].fileName, object : S3FileDownloader.DownloadListener {
                                override fun onDownloadCompleted(file: File?) {
                                    Log.d(Constants.TAG, "파일 다운 성공 여기, $file")
                                    Glide.with(this@OneArticleDetailActivity)
                                        .load(file)
                                        .placeholder(R.drawable.circle_logo)
                                        .error(R.drawable.logo)
                                        .into(here_photo)
                                }

                                override fun onDownloadFailed() {
                                    // 필요한 경우 에러 이미지 표시
                                    Log.d(Constants.TAG, "파일 다운 실패")
                                }
                            })
                        }

                    }
                } else {
                    // 요청이 실패했을 때의 처리
                    val errorBody = response.errorBody()?.string()
                    Log.d(Constants.TAG, "오류ㅜ : $errorBody")
                    // 에러 메시지 등을 처리
                }

            } catch (e: Exception) {
                // 예외 처리
            }
        }
        val mapView = findViewById<MapView>(R.id.mapView)
        mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                // 지도 API 가 정상적으로 종료될 때 호출됨
                Log.d("KaKaoMap", "Successfully Finished")
            }

            override fun onMapError(error: Exception) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
                try {
                    // 여기서 예외를 캐치하고 로그에 기록할 수 있습니다.
                    Log.d("KaKaoMap", "Error occurred: ${error.message}")
                } catch (e: Exception) {
                    // 예외가 발생한 경우에 대한 추가적인 조치를 취할 수 있습니다.
                    e.printStackTrace()
                }
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                // 인증 후 API 가 정상적으로 실행될 때 호출됨
                Log.d("KaKaoMap", "Successfully Working! U R Lucky :D")
            }
        })
    }
}