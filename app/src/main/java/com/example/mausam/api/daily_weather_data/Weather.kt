package com.example.mausam.api.daily_weather_data

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)