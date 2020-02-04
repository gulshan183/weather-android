package com.example.myapplication.ui.weather.data.model

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */
data class CurrentWeatherResponseModel (
    val coord: Coord? = null,
    val weather: List<Weather>? = null,
    val base: String? = null,
    val main: Main? = null,
    val visibility: Long? = null,
    val wind: Wind? = null,
    val clouds: Clouds? = null,
    val dt: Long? = null,
    val id: Long? = null,
    val name: String? = null,
    val cod: Long? = null
)

data class Clouds (
    val all: Long? = null
)

data class Coord (
    val lon: Double? = null,
    val lat: Double? = null
)

data class Main (
    val temp: Double? = null,
    val pressure: Long? = null,
    val humidity: Long? = null,
    val tempMin: Double? = null,
    val tempMax: Double? = null
)

data class Weather (
    val id: Long? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)

data class Wind (
    val speed: Double? = null,
    val deg: Long? = null
)
