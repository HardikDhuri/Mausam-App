package com.example.mausam.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mausam.data_format.database.current_location.CurrLocation

@Dao
interface CurrLocationDao {
    @Insert
    suspend fun insertCurrWeatherData(currLocation: CurrLocation)

    @Insert
    suspend fun updateCurrWeatherData(currLocation: CurrLocation)

    @Delete
    suspend fun deleteCurrWeatherData(currLocation: CurrLocation)

    @Query("Select * from current_location where primaryKey=1")
    suspend fun getCurrWeatherData(): CurrLocation
}
