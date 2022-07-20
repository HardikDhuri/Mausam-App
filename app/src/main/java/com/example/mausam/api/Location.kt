package com.example.mausam.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.mausam.others.Utility.isLocationInitialized
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.concurrent.TimeUnit

class Location(val context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("coords", Context.MODE_PRIVATE)

    init {
        setCoords()
    }

    private fun logCoords() {
        val lat = sharedPref.getString("lat", "0")
        val long = sharedPref.getString("long", "0")
        val time = sharedPref.getLong("timeStampInMins", 0)
        Log.i("Setup", "{$lat $long} $time")
    }

    private fun lastUpdatedMoreThan30MinsAgo(): Boolean {
        val time = sharedPref.getLong("timeStampInMins", Long.MAX_VALUE)
        return if (time == Long.MAX_VALUE) {
            true
        } else {
            val currTime = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())
            val timeDiff = currTime - time
            (timeDiff >= 30)
        }
    }

    private fun setCoords() {
        if (!isLocationInitialized(sharedPref) || lastUpdatedMoreThan30MinsAgo()) {
            val locationProvider = LocationServices.getFusedLocationProviderClient(this.context)
            locationProvider.lastLocation.addOnSuccessListener {
                with(sharedPref.edit()) {
                    putLong(
                        "timeStampInMins",
                        TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())
                    )
                    putString("lat", it.latitude.toString())
                    putString("long", it.longitude.toString())
                    apply()

                }
            }
        }
    }

}