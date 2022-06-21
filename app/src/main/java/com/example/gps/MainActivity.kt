package com.example.gps


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), LocationListener {

    private val PERMISSIONS_REQUEST_CODE = 100
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationStart()
                Log.d("debug", "許可されている")
            } else {
                Log.d("debug", "許可されていない")
                // 許可されていないので許可ダイアログを表示する
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_CODE)
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE ->
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("debug", "許可された")
                    locationStart()
                } else {
                    Log.d("debug", "許可されなかった")
                }
        }
    }


    private fun locationStart() {

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        } else {
            // to prompt setting up GPS
            val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(settingsIntent)
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1000)

            return
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000, //通知間隔 最低1秒
            50f, //通知感覚 最低50m
            this)

    }

    override fun onLocationChanged(location: Location) {
        val textView1 = findViewById<TextView>(R.id.text_view1)
        val str1 = "Latitude:" + location.latitude
        textView1.text = str1

        val textView2 = findViewById<TextView>(R.id.text_view2)
        val str2 = "Longtude:" + location.longitude
        textView2.text = str2

        Log.d("debug", location.latitude.toString() + ":" + location.longitude)

    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onProviderDisabled(provider: String) {

    }
}
