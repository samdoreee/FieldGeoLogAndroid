package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.data.model.Constants
import com.samdoreee.fieldgeolog.data.model.MyRecordModel
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.PersonalRecordResponse
import com.samdoreee.fieldgeolog.ui.adapter.MyRecordAdapter
import kotlinx.coroutines.*
import retrofit2.Response

class MyRecordActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_record)

        val myId: Long = intent.getLongExtra("myId", 0L)  // 0L은 기본값
        Log.d(Constants.TAG, "인텐트 : $myId")

        launch {
            try {
                val response2: Response<List<PersonalRecordResponse>> = withContext(Dispatchers.IO) {
                    GeoApi.retrofitService.getAllRecords()
                }

                if (response2.isSuccessful) {
                    val myPersonalRecordResponse: List<PersonalRecordResponse>? = response2.body()?.filter { it.userId == myId }

                    Log.d(Constants.TAG, "내기록 : ${response2.body()}")
                    if (myPersonalRecordResponse != null) {
                        Log.d(Constants.TAG, "내기록 : $myPersonalRecordResponse")

                        val myRecordModels: MutableList<MyRecordModel> = myPersonalRecordResponse.map { it.convertToMyRecordModel() }.toMutableList()
                        val myrecordlistadapter = MyRecordAdapter(myRecordModels)
                        val myrecordlist = findViewById<ListView>(R.id.myrecordlistview)
                        myrecordlist.adapter = myrecordlistadapter

                        myrecordlist.setOnItemClickListener { parent, view, position, id ->
                            val intent = Intent(this@MyRecordActivity, OneRecordActivity::class.java)
                            startActivity(intent)
                        }
                    }
                } else {
                    // 요청이 실패했을 때의 처리
                    val errorBody = response2.errorBody()?.string()
                    Log.d(Constants.TAG, "오류ㅜ : $errorBody")
                    // 에러 메시지 등을 처리
                }
            } catch (e: Exception) {
                // 예외 처리
            }
            Log.d("", "")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
