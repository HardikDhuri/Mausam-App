package com.example.mausam.api

import com.example.mausam.BuildConfig
import com.example.mausam.api.data.current_weather_data.CurrentWeatherData
import com.example.mausam.api.data.daily_weather_data.DailyWeatherData
import com.example.mausam.api.data.hourly_weather_data.HourlyWeatherData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Weather(private val lat:String, private val long:String) {

    companion object {
        const val BASE_URL = "https://pro.openweathermap.org/"
        const val API_KEY = BuildConfig.API_KEY
        const val UNITS = "metric"
        const val HOUR_COUNTS = "24"
        const val DAILY_COUNTS = "10"
    }

    suspend fun getCurrWeatherData(): CurrentWeatherData {
            val weatherApi = getWeatherApi()
            return weatherApi.getCurrentLocationWeatherData(
                latitude = this@Weather.lat,
                longitude = this@Weather.long,
                key = API_KEY,
                units = UNITS
            )
    }

    suspend fun getHourlyWeatherData(): HourlyWeatherData {
        val weatherApi = getWeatherApi()
        return weatherApi.get24HourWeatherData(
            latitude = this@Weather.lat,
            longitude = this@Weather.long,
            key = API_KEY,
            units = UNITS,
            count = HOUR_COUNTS
        )
    }

    suspend fun getdailyWeatherData(): DailyWeatherData {
        val weatherApi = getWeatherApi()
        return weatherApi.get10DaysWeartherData(
            latitude = this@Weather.lat,
            longitude = this@Weather.long,
            key = API_KEY,
            units = UNITS,
            count = DAILY_COUNTS
        )
    }

    private fun getWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}

