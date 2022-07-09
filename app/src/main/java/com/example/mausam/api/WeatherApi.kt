package com.example.mausam.api

import com.example.mausam.api.data.current_weather_data.CurrentWeatherData
import com.example.mausam.api.data.daily_weather_data.DailyWeatherData
import com.example.mausam.api.data.hourly_weather_data.HourlyWeatherData
import com.example.mausam.api.data.search_query_data.SearchQueryData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/weather")
    suspend fun getCurrentLocationWeatherData(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") key: String,
        @Query("units") units: String

    ): CurrentWeatherData

    @GET("/geo/1.0/direct")
    suspend fun getSearchLocations(
        @Query("q") latitude: String,
        @Query("limit") longitude: Int,
        @Query("appid") key: String,
        @Query("units") units: String
    ): SearchQueryData

    @GET("/data/2.5/forecast/hourly")
    suspend fun get24HourWeatherData(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") key: String,
        @Query("cnt") count: String,
        @Query("units") units: String
    ): HourlyWeatherData

    @GET("/data/2.5/forecast/daily")
    suspend fun get10DaysWeartherData (
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") key: String,
        @Query("cnt") count: String,
        @Query("units") units: String
    ): DailyWeatherData
}