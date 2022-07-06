package com.example.mausam.api.daily_weather_data

data class DailyWeatherData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<DailyData>,
    val message: Double
)