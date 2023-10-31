package com.samdoreee.fieldgeolog.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.samdoreee.fieldgeolog.R

class GeoMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geo_map)

        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        val html = """
            <!DOCTYPE html>
            <html>
            <head>
               <title>지오빅데이터 오픈플랫폼 오픈API leaflet 샘플</title>
               <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.3/dist/leaflet.css"
                 integrity="sha256-kLaT2GOSpHechhsozzB+flnD+zUyjE2LlfWPgU04xyI="
                 crossorigin=""/>
               <script src="https://unpkg.com/leaflet@1.9.3/dist/leaflet.js"
                 integrity="sha256-WBkoXOwTeyKclOHuWtc+i2uENFpDZ9YPdf5Hf+D7ewM="
                 crossorigin=""></script>
            </head>
            <body>
               <div id="map" style="height: 140vh"></div>
               <div id="info"></div>
               <script>
                   var map = L.map('map').setView([35.95377809315542, 128.17342348527225], 13);
                   L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
                   maxZoom: 19,
                   attribution: '© <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
                   }).addTo(map);

                  L.tileLayer.wms('https://data.kigam.re.kr/openapi/wms',{
                     layers: 'L_250K_Geology_Map',
                     key: 'jU63oAHv0qTm2ouzsjASXpuGipQBd2',
                    FORMAT: 'image/png',
                    transparent: true
                  }).addTo(map);
               </script>
            </body>
            </html>
        """.trimIndent()

        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)

        val gotohome = findViewById<ImageButton>(R.id.back_to_home_btn)
        gotohome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val floatingbtn = findViewById<CardView>(R.id.add_new_spot_btn)
        floatingbtn.setOnClickListener {
            val intent = Intent(this, RecordWriteActivity::class.java)
            startActivity(intent)
        }
        val mapbtn = findViewById<FloatingActionButton>(R.id.on_geomap_btn)
        mapbtn.setOnClickListener {
            val intent = Intent(this, NewRecordActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "일반 지도 모드로 전환합니다", Toast.LENGTH_SHORT).show()
        }

    }
}