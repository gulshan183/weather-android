package com.example.myapplication.ui.weather.data

import com.example.myapplication.ui.weather.data.model.CurrentWeatherResponseModel
import com.example.myapplication.ui.weather.data.model.WeatherForecastResponseModel

/**
 * Created by Gulshan Ahluwalia on 2020-02-06.
 */
interface WeatherRepository {
    suspend fun fetchWeatherForecast(
        latitude: Double,
        longitude: Double
    ): WeatherForecastResponseModel?

    suspend fun fetchCurrentWeather(cityName: String): CurrentWeatherResponseModel?
}