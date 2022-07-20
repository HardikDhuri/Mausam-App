package com.example.mausam.others

object Contants {
    const val IMAGE_URL = "https://openweathermap.org/"
    const val REQUEST_CODE_LOCATION_PERMISSIONS = 0
    var LAT = "0"
    var LONG = "0"


}
fun Contants.setCoords(lat: String, long: String) {
    Contants.LAT = lat
    Contants.LONG = long
}

fun Contants.getLat(): String {
    return LAT
}

fun Contants.getLong(): String{
    return LONG
}


