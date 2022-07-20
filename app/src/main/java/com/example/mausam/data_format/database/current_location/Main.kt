package com.example.mausam.data_format.database.current_location

import androidx.room.Entity

@Entity(tableName = "main")
data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)