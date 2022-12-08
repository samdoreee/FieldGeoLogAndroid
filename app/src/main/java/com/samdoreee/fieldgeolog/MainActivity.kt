package com.samdoreee.fieldgeolog

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.samdoreee.fieldgeolog.databinding.MainBinding
import com.samdoreee.fieldgeolog.network.GeoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.CurrentLocationTrackingMode


class MainActivity : AppCompatActivity() {
    private lateinit var writeRecycler: RecyclerView

    private lateinit var binding: MainBinding
    private lateinit var mapView: MapView
    private val ACCESS_FINE_LOCATION = 1000     // Request Code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainBinding.inflate(layoutInflater)
        val view = binding.root
        mapView = binding.mapView
        setContentView(view)
        /*writeRecycler = findViewById(R.id.)*/

        permissionCheck()

        // (좌) 기록된 위치 표시
        binding.btnStart.setOnClickListener {
            mapView.removeAllPOIItems()
            runBlocking {
                stopTracking()
                val allSpots = withContext(Dispatchers.Default) {
                    GeoApi.retrofitService.getAllSpots()
                }
                for (it in allSpots.withIndex()) {
                    addMarker(it.value.latitude, it.value.longitude, it.value.weatherInfo)
                    delay(2000)
                    mapView.setMapCenterPoint(
                        MapPoint.mapPointWithGeoCoord(it.value.latitude, it.value.longitude),
                        true
                    )
                }
                permissionCheck()
            }
        }

        // (중앙) 기록시작 모드로 전환
        binding.btnRecordStart.setOnClickListener {
            val intent = Intent(this, ProjectListActivity::class.java)
            startActivity(intent)
        }

        // (플로팅버튼) 기록 모드로 전
        binding.btnToWriteactivity.setOnClickListener {
            val intent = Intent(this, WriteActivity::class.java)
            if (checkLocationService()) {
                // GPS가 켜져있을 경우
                val curGeoCoord = mapView.mapCenterPoint.mapPointGeoCoord
                intent.putExtra("latitude", curGeoCoord.latitude)
                intent.putExtra("longitude", curGeoCoord.longitude)
                startActivity(intent)
                addMarker(curGeoCoord.latitude, curGeoCoord.longitude, "currently added spot")
            } else {
                // GPS가 꺼져있을 경우
                Toast.makeText(this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopTracking()
    }

    // 위치 권한 확인
    private fun permissionCheck() {
        val preference = getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없는 상태
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // 권한 거절 (다시 한 번 물어봄)
                val builder = AlertDialog.Builder(this)
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        ACCESS_FINE_LOCATION
                    )
                }
                builder.setNegativeButton("취소") { dialog, which ->

                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    // 최초 권한 요청
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        ACCESS_FINE_LOCATION
                    )
                } else {
                    // 다시 묻지 않음 클릭 (앱 정보 화면으로 이동)
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:$packageName")
                        )
                        startActivity(intent)
                    }
                    builder.setNegativeButton("취소") { dialog, which ->

                    }
                    builder.show()
                }
            }
        } else {
            Log.d("test", "권한 확인 완료")

            // 권한이 있는 상태
            startTracking()
        }
    }

    // 권한 요청 후 행동
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 요청 후 승인됨 (추적 시작)
                Toast.makeText(this, "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()
                startTracking()
            } else {
                // 권한 요청 후 거절됨 (다시 요청 or 토스트)
                Toast.makeText(this, "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show()
                runBlocking { permissionCheck() }
            }
        }
    }

    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // 위치추적 시작
    private fun startTracking() {
        binding.mapView.currentLocationTrackingMode =
            CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
    }

    // 위치추적 중지
    private fun stopTracking() {
        binding.mapView.currentLocationTrackingMode = CurrentLocationTrackingMode.TrackingModeOff
    }

    private fun addMarker(lat: Double, long: Double, name: String) {
        val marker = MapPOIItem()
        marker.apply {
            itemName = name   // 마커 이름
            mapPoint = MapPoint.mapPointWithGeoCoord(lat, long)
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = R.drawable.custom_marker_red
            selectedMarkerType = MapPOIItem.MarkerType.CustomImage
            customSelectedImageResourceId = R.drawable.custom_marker_red
            isCustomImageAutoscale = false
            setCustomImageAnchor(0.25f, 0.5f)
        }
        mapView.addPOIItem(marker)
    }
}
