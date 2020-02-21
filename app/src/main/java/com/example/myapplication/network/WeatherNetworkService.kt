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

    /**
     * Call for getting current weather of particular city
     *
     * @param cityName
     * @return [Call]
     */
    @GET("weather")
    fun getCurrentWeather(@Query("q") cityName: String): Call<CurrentWeatherResponseModel>

    /**
     * Call for weather forecast of 5 days with 3 hours interval for the defined location
     *
     * @param lat
     * @param lon
     * @return [Call]
     */
    @GET("forecast")
    fun getForecastWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<WeatherForecastResponseModel>
}