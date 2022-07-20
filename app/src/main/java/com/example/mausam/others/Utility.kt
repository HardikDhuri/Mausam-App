package com.example.mausam.others

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.mausam.others.Contants.REQUEST_CODE_LOCATION_PERMISSIONS
import com.example.mausam.ui.fragments.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_home.*
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*

object Utility {
    fun hasLocationPermissions(context: Context) =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }



    fun requestPermissions(host: HomeFragment) {
        EasyPermissions.requestPermissions(
            host,
            "You need to accept location permissions to use this app.",
            REQUEST_CODE_LOCATION_PERMISSIONS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    fun requesrPermissionForBackgroundLocation(host: HomeFragment) {
        EasyPermissions.requestPermissions(
            host,
            "You need to set location permissions to \"All the time\". So that app can get the weather forecast appropriately.",
            REQUEST_CODE_LOCATION_PERMISSIONS,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    }

    fun isLocationInitialized(sharedPref: SharedPreferences): Boolean {
        return (sharedPref.getString("lat", null) != null && sharedPref.getString("long", null) != null)
    }

    fun getDayOfWeek(timestamp: Long): String {
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
    }

    fun getDate(timestamp: Long): String {
        return SimpleDateFormat("MMMM d, YYYY", Locale.ENGLISH).format(timestamp * 1000)
    }

    fun getShortDate(timestamp: Long): String {
        return SimpleDateFormat("MMMM, dd", Locale.ENGLISH).format(timestamp * 1000)
    }


}