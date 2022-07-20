package com.example.mausam.data_format.database.current_location

import androidx.room.Entity

@Entity(tableName = "sys")
data class Sys(
    val country: String,
    val sunrise: Int,
    val sunset: Int
)