package com.example.laboratorio_mapas

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Camera
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import android.Manifest.permission

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var locationManager: LocationManager? = null
    private var LocationListener: LocationListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json))
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
//        val uamLocation=LatLng(12.10917882939882, -86.25698711415158)
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(uamLocation))
//        val marker=MarkerOptions().position(uamLocation).title("Nuestra Ubicacion")
//        mMap.addMarker(marker)
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uamLocation,16f))
//        mMap.uiSettings.isZoomControlsEnabled=true
//        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.style_json)
        LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val currLocation = LatLng(location.latitude, location.longitude)
                val marker = MarkerOptions().position(currLocation).title("Nuestra Ubicacion")
//              mMap.addMarker(marker)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation,16f))
            }

        }
        if (
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100_00
            )
        } else {
            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                60000,
                10f,
                LocationListener as LocationListener
            )
        }


    }


}