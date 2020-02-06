package com.example.myapplication.ui.weather.data

import com.example.myapplication.R
import com.example.myapplication.constant.APIResponseState.*
import com.example.myapplication.network.ErrorResponseModel
import com.example.myapplication.network.NO_INTERNET
import com.example.myapplication.ui.weather.data.model.City
import com.example.myapplication.ui.weather.data.model.CurrentWeatherResponseModel
import com.example.myapplication.ui.weather.data.model.ListElement
import com.example.myapplication.ui.weather.data.model.WeatherForecastResponseModel
import java.util.*

/**
 * Created by Gulshan Ahluwalia on 2020-02-06.
 */
class FakeWeatherRepository : WeatherRepository {
    var apiResponseState = SUCCESS
    override suspend fun fetchWeatherForecast(
        latitude: Double,
        longitude: Double
    ): WeatherForecastResponseModel? {
        when (apiResponseState) {
            SUCCESS -> {
                return WeatherForecastResponseModel(
                    city = City(
                        name = "London"
                    ),
                    list = listOf(
                        ListElement(
                            dt = System.currentTimeMillis() / 1000
                        ), ListElement(
                            dt = (System.currentTimeMillis() + 60 * 1000 * 60) / 1000
                        ), ListElement(
                            dt = (System.currentTimeMillis() + (1000 * 60 * 60 * 25)) / 1000
                        )
                    )
                )
            }
            API_KEY_ERROR -> {
                throw NetworkWeatherRepository.RepositoryException.WeatherForecastException(
                    errorResponseModel = ErrorResponseModel(
                        msgRes = R.string.something_went_wrong,
                        errorCode = "401"
                    )
                )
            }
            NETWORK_ERROR -> {
                throw NetworkWeatherRepository.RepositoryException.WeatherForecastException(
                    errorResponseModel = ErrorResponseModel(
                        msgRes = R.string.unreachable_server_internet_error,
                        errorCode = NO_INTERNET
                    )
                )
            }
            NOT_FOUND -> {
                return WeatherForecastResponseModel(
                    city = City(
                        name = "London"
                    )
                )
            }
        }
    }

    override suspend fun fetchCurrentWeather(cityName: String): CurrentWeatherResponseModel? {
        when (apiResponseState) {
            SUCCESS -> {
                return CurrentWeatherResponseModel(
                    dt = 100000,
                    id = Random().nextLong(),
                    name = "London"
                )
            }
            API_KEY_ERROR -> {
                throw NetworkWeatherRepository.RepositoryException.CurrentWeatherException(
                    errorResponseModel = ErrorResponseModel(
                        msgRes = R.string.something_went_wrong,
                        errorCode = "401"
                    )
                )
            }
            NETWORK_ERROR -> {
                throw NetworkWeatherRepository.RepositoryException.CurrentWeatherException(
                    errorResponseModel = ErrorResponseModel(
                        msgRes = R.string.unreachable_server_internet_error,
                        errorCode = NO_INTERNET
                    )
                )
            }
            NOT_FOUND -> {
                throw NetworkWeatherRepository.RepositoryException.CurrentWeatherException(
                    errorResponseModel = ErrorResponseModel(
                        msgRes = R.string.something_went_wrong,
                        errorCode = com.example.myapplication.network.NOT_FOUND
                    )
                )
            }
        }
    }
}
