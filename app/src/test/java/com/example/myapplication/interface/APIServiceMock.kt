package com.example.myapplication.`interface`

import com.example.myapplication.constant.APIResponseState.*
import com.example.myapplication.network.WeatherNetworkService
import com.example.myapplication.ui.weather.data.model.City
import com.example.myapplication.ui.weather.data.model.CurrentWeatherResponseModel
import com.example.myapplication.ui.weather.data.model.WeatherForecastResponseModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls
import java.io.IOException

/**
 * Created by Gulshan Ahluwalia on 2020-02-06.
 */

class APIServiceMock(private val delegate: BehaviorDelegate<WeatherNetworkService?>) :
    WeatherNetworkService {

    var apiResponseState = SUCCESS


    override fun getCurrentWeather(cityName: String): Call<CurrentWeatherResponseModel> {
        return (when (apiResponseState) {
            SUCCESS -> {
                delegate.returningResponse(
                    CurrentWeatherResponseModel(
                        dt = 1021212
                    )

                )
            }
            NOT_FOUND -> {
                delegate.returning(
                    Calls.response(
                        Response.error<CurrentWeatherResponseModel>(
                            404,
                            ("{\"cod\":\"404\",\"message\":\"city not found\"}").toResponseBody("application/json".toMediaType())
                        )
                    )
                )
            }
            API_KEY_ERROR -> {
                delegate.returning(
                    Calls.response(
                        Response.error<CurrentWeatherResponseModel>(
                            401,
                            ("{\"cod\":\"401\",\"message\":\"Invalid API Key\"}").toResponseBody("application/json".toMediaType())
                        )
                    )
                )
            }
            NETWORK_ERROR -> {
                delegate.returning(Calls.failure<IOException>(IOException("Some error")))
            }
        })!!.run {
            this.getCurrentWeather(cityName)
        }

    }

    override fun getForecastWeather(lat: Double, lon: Double): Call<WeatherForecastResponseModel> {
        return (when (apiResponseState) {
            SUCCESS -> {
                delegate.returningResponse(
                    WeatherForecastResponseModel(
                        city = City(
                            name = "London"
                        )
                    )

                )
            }
            NOT_FOUND -> {
                delegate.returning(
                    Calls.response(
                        Response.error<CurrentWeatherResponseModel>(
                            404,
                            ("{\"cod\":\"404\",\"message\":\"city not found\"}").toResponseBody("application/json".toMediaType())
                        )
                    )
                )
            }
            API_KEY_ERROR -> {
                delegate.returning(
                    Calls.response(
                        Response.error<CurrentWeatherResponseModel>(
                            401,
                            ("{\"cod\":\"401\",\"message\":\"Invalid API Key\"}").toResponseBody("application/json".toMediaType())
                        )
                    )
                )
            }
            NETWORK_ERROR -> {
                delegate.returning(Calls.failure<IOException>(IOException("Some error")))
            }
        })!!.run {
            this.getForecastWeather(lat, lon)
        }
    }

}
