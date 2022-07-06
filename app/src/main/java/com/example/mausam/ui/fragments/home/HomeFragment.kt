package com.example.mausam.ui.fragments.home

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mausam.R
import com.example.mausam.adapters.HourlyWeatherAdapter
import com.example.mausam.adapters.WeeklyWeatherAdapter
import com.example.mausam.api.WeatherApi
import com.example.mausam.data.WeatherPerDay
import com.example.mausam.data.WeatherPerHour
import com.example.mausam.others.Contants.BASE_URL
import com.example.mausam.others.Utility
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(R.layout.fragment_home), EasyPermissions.PermissionCallbacks {
    private lateinit var locationProvider: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()


        val weatherList = mutableListOf<WeatherPerHour>(
            WeatherPerHour("Apple", "14:00", "32°C"),
            WeatherPerHour("Apple", "15:00", "12°C"),
            WeatherPerHour("Apple", "16:00", "62°C"),
            WeatherPerHour("Apple", "17:00", "32°C"),
            WeatherPerHour("Apple", "18:00", "72°C"),
            WeatherPerHour("Apple", "19:00", "22°C"),
            WeatherPerHour("Apple", "20:00", "92°C"),
            WeatherPerHour("Apple", "21:00", "22°C"),
            WeatherPerHour("Apple", "22:00", "42°C"),
            WeatherPerHour("Apple", "23:00", "32°C"),
        )
        val weeklyList = mutableListOf<WeatherPerDay>(
            WeatherPerDay("Friday", "July, 4", "32°C", "Apple"),
            WeatherPerDay("Saturday", "July, 5", "52°C", "Apple"),
            WeatherPerDay("Sunday", "July, 6", "72°C", "Apple"),
            WeatherPerDay("Monday", "July, 7", "32°C", "Apple"),
            WeatherPerDay("Tuesday", "July, 8", "62°C", "Apple"),
            WeatherPerDay("Wednesday", "July, 9", "22°C", "Apple"),
            WeatherPerDay("Friday", "July, 10", "99°C", "Apple"),
        )

        val weatherApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)



        setHourlyWeatherAdapter(weatherList)
        setWeeklyWeatherAdapter(weeklyList)
        CoroutineScope(IO).launch {
//            val weatherData = weatherApi.getCurrentLocationWeatherData(
//                key = API_KEY,
//                latitute = "30",
//                longitute = "139"
//            )
//            Log.d("JSON DATA WEATHER","Data: ${weatherData}")
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
            }
    }

    private fun setHourlyWeatherAdapter(hourlyWeatherList: List<WeatherPerHour>) {
        val hourlyWeatherAdapter = HourlyWeatherAdapter(hourlyWeatherList)
        rv_hours_weather.adapter = hourlyWeatherAdapter
        rv_hours_weather.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setWeeklyWeatherAdapter(weeklyWeatherList: List<WeatherPerDay>) {
        val weeklyWeatherAdapter = WeeklyWeatherAdapter(weeklyWeatherList)
        rv_weekly_weather.adapter = weeklyWeatherAdapter
        rv_weekly_weather.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    private fun requestPermissions() {
        if (Utility.hasLocationPermissions(requireContext())) {
            Snackbar.make(home_fragment, "Has Required Permissions", Snackbar.LENGTH_SHORT).show()
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            Utility.requestPermissionsForLtAndroidQ(this)
        } else {
            Utility.requestPermissionsForGtAndroidQ(this)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


}