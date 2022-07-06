package com.example.mausam.api.hourly_weather_data

data class HourlyWeatherData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<HourlyData>,
    val message: Int
)