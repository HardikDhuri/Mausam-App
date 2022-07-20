package com.example.mausam.data_format.database.current_location

import androidx.room.Entity

@Entity(tableName = "weather")
data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)