package com.example.mausam.ui.fragments.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mausam.R
import com.example.mausam.adapters.HourlyWeatherAdapter
import com.example.mausam.adapters.WeeklyWeatherAdapter
import com.example.mausam.api.Weather
import com.example.mausam.api.data.current_weather_data.CurrentWeatherData
import com.example.mausam.api.data.daily_weather_data.DailyData
import com.example.mausam.api.data.daily_weather_data.DailyWeatherData
import com.example.mausam.api.data.hourly_weather_data.HourlyWeatherData
import com.example.mausam.api.data.hourly_weather_data.MainHourlyWeather
import com.example.mausam.data.WeatherPerDay
import com.example.mausam.data.WeatherPerHour
import com.example.mausam.others.*
import com.example.mausam.others.Contants.LAT
import com.example.mausam.others.Contants.LONG
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home), EasyPermissions.PermissionCallbacks {
    private lateinit var locationProvider: FusedLocationProviderClient
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = requireContext().getSharedPreferences("coords", Context.MODE_PRIVATE)
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

        CoroutineScope(Dispatchers.IO).launch {
            val lat = sharedPref.getString("lat", "0").toString()
            val long = sharedPref.getString("long", "0").toString()
            val weather = Weather("51.5072", "0.1276")
            val currWeatherData= weather.getCurrWeatherData()
            val hourlyWeatherData = weather.getHourlyWeatherData()
            Log.d("Hourly Data", hourlyWeatherData.toString())
            withContext(Dispatchers.Main) {
                setCurrentWeatherCardView(currWeatherData)
                setHourlyWeatherAdapter(setHourlyWeatherView(weather.getHourlyWeatherData()))
                setWeeklyWeatherAdapter(setDailyWeatherView(weather.getWeeklyWeatherData()))
            }
        }

    }

    private fun setLat(lat: String) {
        LAT = lat
    }

    private fun setLong(long: String) {
        LONG = long
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
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            Utility.requestPermissions(this)
        } else {
            Utility.requestPermissions(this)
            Utility.requesrPermissionForBackgroundLocation(this)
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

    private fun log(weatherData: CurrentWeatherData) {
        Log.d("Json", weatherData.toString())
    }

    private fun setCurrentWeatherCardView(weatherData: CurrentWeatherData) {
        current_locatin_title.text = weatherData.name
        date_text_view.text = getDate(weatherData.dt.toLong())
        temp_value.text = weatherData.main.temp.toString()+"°C"
        wind_value.text = weatherData.wind.speed.toString()+" km/h"
        humidity_value.text = weatherData.main.humidity.toString()+"%"
        Glide.with(requireContext()).load("${Contants.IMAGE_URL}/img/wn/${weatherData.weather[0].icon}@4x.png").into(current_weather_icon)
    }

    private fun setHourlyWeatherView(weather: HourlyWeatherData): List<WeatherPerHour> {
        val weatherList = mutableListOf<WeatherPerHour>()
        val mailWeatherList: List<MainHourlyWeather> = weather.list
        for (item in mailWeatherList) {
            val time = item.dt_txt.slice(11..15)
            val icon = item.weather[0].icon
            val temp = "${item.main.temp.toString()}°C"
            weatherList.add(WeatherPerHour(icon, time, temp))
        }
        return weatherList
    }

    private fun setDailyWeatherView(weather: DailyWeatherData): List<WeatherPerDay> {
        val weatherList = mutableListOf<WeatherPerDay>()
        val mailWeatherList: List<DailyData> = weather.list
        for (item in mailWeatherList) {
            val day = getDayOfWeek(item.dt.toLong())
            val date = getShortDate(item.dt.toLong())
            val icon = item.weather[0].icon
            val temp = "${item.temp.day}°C"
            weatherList.add(WeatherPerDay(day, date, temp, icon))
        }
        return weatherList
    }

    private fun getDayOfWeek(timestamp: Long): String {
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
    }

    private fun getDate(timestamp: Long): String {
        return SimpleDateFormat("MMMM d, YYYY", Locale.ENGLISH).format(timestamp * 1000)
    }

    private fun getShortDate(timestamp: Long): String {
        return SimpleDateFormat("MMMM, dd", Locale.ENGLISH).format(timestamp * 1000)
    }
}