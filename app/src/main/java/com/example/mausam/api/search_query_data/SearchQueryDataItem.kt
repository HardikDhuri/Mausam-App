package com.example.mausam.api.search_query_data

data class SearchQueryDataItem(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String
)