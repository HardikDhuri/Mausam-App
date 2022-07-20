package com.example.mausam.data_format.database.current_location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_location")
data class CurrLocation(
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int,
    val id: Int,
    val base: String,
    val cod: Int,
    val coord_lat: Double,
    val coord_lon: Double,
    val dt: Int,
    val main_feels_like: Double,
    val main_humidity: Int,
    val main_temp: Double,
    val name: String,
    val sys_country: String,
    val timezone: Int,
    val visibility: Int,
    val weather_description: String,
    val weather_icon: String,
    val weather_main: String,
    val wind_speed: Double
)
