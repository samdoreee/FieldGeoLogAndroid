package com.samdoreee.fieldgeolog

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.samdoreee.fieldgeolog.network.GeoApi
import com.samdoreee.fieldgeolog.network.SpotRequest
import com.samdoreee.fieldgeolog.record.Project
import com.samdoreee.fieldgeolog.record.Record
import kotlinx.coroutines.runBlocking


class WriteActivity : AppCompatActivity() {
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var project_title: EditText
    private lateinit var project_date: EditText
    private lateinit var project_location: EditText
    private lateinit var project_weather: EditText
    private lateinit var project_thumbnail: String
    private lateinit var dip: EditText
    private lateinit var strike: EditText
    private lateinit var rocktype: EditText
    private lateinit var geo_struct: EditText
    val projectList = TempMemory.tempprojectmemory
    val recordList = TempMemory.temprecordmemory

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        btnSave = findViewById(R.id.btn_save)
        btnCancel = findViewById(R.id.btn_cancel)
        project_title = findViewById(R.id.title)
        project_date = findViewById(R.id.title)
        project_location = findViewById(R.id.location)
        project_weather = findViewById(R.id.weather)
        project_thumbnail = "samdoree"
        strike = findViewById(R.id.strike)
        dip = findViewById(R.id.dip)
        rocktype = findViewById(R.id.rocktype)
        geo_struct = findViewById(R.id.geo_struct)

        btnSave.setOnClickListener {
            save()
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnCancel.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun save() {
        val title = project_title.text.toString()
        val location = project_location.text.toString()
        val weather = project_weather.text.toString()
        val match = Regex("(\\d+)([A-Z]+)").find(strike.text.toString())!!
        val strike = match.destructured.component1().toInt()
        val direction = match.destructured.component2()
        val dip = dip.text.toString().toInt()
        val rockType = rocktype.text.toString()
        val geoStructure = geo_struct.text.toString()
        val date = project_date.text.toString()

        val rec_add = Record(
            title,
            project_thumbnail,
            date,
            location,
            weather,
            strike.toString(),
            dip.toString(),
            rockType,
            geoStructure
        )
        recordList.add(rec_add)

        val project_add = Project(
            rec_add.project_title,
            rec_add.project_date,
            rec_add.project_location,
            rec_add.project_thumbnail
        )
        projectList.add(project_add)

        runBlocking {
            val newSpot = SpotRequest(
                latitude = intent.getDoubleExtra("latitude", 0.0),
                longitude = intent.getDoubleExtra("longitude", 0.0),
                strike = strike,
                rockType = rockType,
                geoStructure = geoStructure,
                dip = dip,
                direction = direction
            )
            println("newSpot = ${newSpot}")
            GeoApi.retrofitService.addSpot(newSpot)
        }
    }

    private fun onClickBtnSave() {
        val intent = Intent(this, ProjectListActivity::class.java)
        startActivity(intent)
    }

}