package com.example.myapplication.network

import com.example.myapplication.ui.weather.data.model.CurrentWeatherResponseModel
import com.example.myapplication.ui.weather.data.model.WeatherForecastResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Gulshan Ahluwalia on 2020-02-03.
 */
interface WeatherNetworkService {

    @GET("weather")
    fun getCurrentWeather(@Query("q") cityName: String): Call<CurrentWeatherResponseModel>

    @GET("forecast")
    fun getForecastWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<WeatherForecastResponseModel>
}