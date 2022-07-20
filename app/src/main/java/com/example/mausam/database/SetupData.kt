package com.example.mausam.database

import android.content.Context
import android.util.Log
import com.example.mausam.api.Location
import com.example.mausam.api.Weather
import com.example.mausam.data_format.database.current_location.CurrLocation
import com.example.mausam.database.db.WeatherDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SetupData(val context: Context) {
    private var updated = false
    private lateinit var lat: String
    private lateinit var long: String
    private val sharedPref = this.context.getSharedPreferences("coords", Context.MODE_PRIVATE)
    private val database = WeatherDatabase.getDatabase(this.context)

     fun setup() {
         CoroutineScope(IO).launch {
             Location(this@SetupData.context)
         }
    }



    private suspend fun getDataAndSave(weather: Weather) {
        val currWeatherData = weather.getCurrWeatherData()
        database.currLocationDao().insertCurrWeatherData(
                    currWeatherData.let {
                                CurrLocation(
                                    0,
                                    id = it.id,
                                    base = it.base,
                                    cod = it.cod,
                                    coord_lat = it.coord.lat,
                                    coord_lon = it.coord.lon,
                                    dt = it.dt,
                                    main_feels_like = it.main.feels_like,
                                    main_humidity = it.main.humidity,
                                    main_temp = it.main.temp,
                                    name = it.name,
                                    sys_country = it.sys.country,
                                    timezone = it.timezone,
                                    visibility = it.visibility,
                                    weather_description = it.weather[0].description,
                                    weather_icon = it.weather[0].icon,
                                    weather_main = it.weather[0].main,
                                    wind_speed = it.wind.speed
                        )
                    }
                )
    }


}