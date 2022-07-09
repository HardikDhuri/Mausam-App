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
        return (sharedPref.getString("lat", "0") != "0" && sharedPref.getString("long", "0") != "0")
    }


}