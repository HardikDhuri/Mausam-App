package com.example.mausam.ui.fragments.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mausam.R
import com.example.mausam.adapters.HourlyWeatherAdapter
import com.example.mausam.adapters.WeeklyWeatherAdapter
import com.example.mausam.api.data.daily_weather_data.DailyData
import com.example.mausam.api.data.daily_weather_data.DailyWeatherData
import com.example.mausam.api.data.hourly_weather_data.HourlyWeatherData
import com.example.mausam.api.data.hourly_weather_data.MainHourlyWeather
import com.example.mausam.data_format.api.WeatherPerDay
import com.example.mausam.data_format.api.WeatherPerHour
import com.example.mausam.database.SetupData
import com.example.mausam.data_format.database.current_location.CurrLocation
import com.example.mausam.database.db.WeatherDatabase
import com.example.mausam.others.*
import com.example.mausam.others.Utility.getDate
import com.example.mausam.others.Utility.getDayOfWeek
import com.example.mausam.others.Utility.getShortDate
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class HomeFragment : Fragment(R.layout.fragment_home), EasyPermissions.PermissionCallbacks {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var database: WeatherDatabase

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
        database = WeatherDatabase.getDatabase(requireContext())

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


    private fun setCurrentWeatherCardView(weatherData: CurrLocation) {
        current_locatin_title.text = weatherData.name
        date_text_view.text = getDate(weatherData.dt.toLong())
        temp_value.text = weatherData.main_temp.toString()+"°C"
        wind_value.text = weatherData.wind_speed.toString()+" km/h"
        humidity_value.text = weatherData.main_humidity.toString()+"%"
        Glide.with(requireContext()).load("${Contants.IMAGE_URL}/img/wn/${weatherData.weather_icon}@4x.png").into(current_weather_icon)
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
}