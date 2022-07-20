package com.example.mausam.data_format.database.current_location

import androidx.room.Entity

@Entity(tableName = "clouds")
data class Clouds(
    val all: Int
)