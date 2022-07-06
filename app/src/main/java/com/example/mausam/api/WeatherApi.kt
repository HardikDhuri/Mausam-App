package com.example.mausam.api

import com.example.mausam.api.current_weather_data.WeatherData
import com.example.mausam.api.search_query_data.SearchQueryData
import com.example.mausam.api.search_query_data.SearchQueryDataItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/weather")
    suspend fun getCurrentLocationWeatherData(
        @Query("lat") latitute: String,
        @Query("lon") longitute: String,
        @Query("appid") key: String
    ): WeatherData

    @GET("/geo/1.0/direct")
    suspend fun getSearchLocations(
        @Query("q") latitute: String,
        @Query("limit") longitute: Int,
        @Query("appid") key: String
    ): SearchQueryData

    @GET("/data/2.5/forecast/hourly")
    suspend fun get24HourWeatherData(
        @Query("lat") latitute: String,
        @Query("lon") longitute: String,
        @Query("appid") key: String,
        @Query("cnt") count: String
    )

    @GET("/data/2.5/forecast/daily")
    suspend fun get10DaysWeartherData(
        @Query("lat") latitute: String,
        @Query("lon") longitute: String,
        @Query("appid") key: String,
        @Query("cnt") count: String
    )



}