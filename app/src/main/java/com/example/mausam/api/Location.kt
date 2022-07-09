package com.example.mausam.api

import android.content.Context
import android.content.SharedPreferences
import com.example.mausam.R
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Location(val context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences("coords", Context.MODE_PRIVATE)

    init {
        initLocation()
    }

    private fun initLocation() {
        CoroutineScope(Dispatchers.IO).launch {
            val locationProvider = LocationServices.getFusedLocationProviderClient(context)
            locationProvider.lastLocation.addOnSuccessListener {
                with (sharedPref.edit()) {
                    putString("lat", it.latitude.toString())
                    putString("long", it.longitude.toString())
                    apply()
                }
            }
        }
    }

    fun getCoords(): HashMap<String, String> {
        val coords = HashMap<String, String>()
        val lat = sharedPref.getString("lat", "0")
        val long = sharedPref.getString("long", "0")
        coords["lat"] = lat.toString()
        coords["long"] = long.toString()
        return coords
    }
}