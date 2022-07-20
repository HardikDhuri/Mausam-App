package com.example.mausam.data_format.database.current_location

import androidx.room.Entity

@Entity(tableName = "coord")
data class Coord(
    val lat: Double,
    val lon: Double
)