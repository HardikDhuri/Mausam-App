package com.example.mausam.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mausam.data_format.database.current_location.CurrLocation
import com.example.mausam.database.dao.CurrLocationDao

@Database(entities = [CurrLocation::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun currLocationDao(): CurrLocationDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            WeatherDatabase::class.java,
                            "weatherDB"
                        )
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}