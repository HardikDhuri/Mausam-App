package com.example.mausam.data_format.database.current_location

import androidx.room.Entity

@Entity(tableName = "wind")
data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)