package com.example.mausam.api.data.hourly_weather_data

data class HourlyWeatherData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<MainHourlyWeather>,
    val message: Int
)