package com.example.myapplication.ui.weather.forecast

/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */
data class ForecastWeatherModel(
    val weatherHours: List<WeatherTime>? = null,
    val date: String? = null
)

data class WeatherTime(
    val minTemp: String? = null,
    val maxTemp: String? = null,
    val windSpeed: String? = null,
    val description: String? = null,
    val time: String? = null
)