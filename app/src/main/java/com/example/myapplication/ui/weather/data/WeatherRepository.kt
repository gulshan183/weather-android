package com.example.myapplication.ui.weather.data

import com.example.myapplication.network.ErrorResponseModel
import com.example.myapplication.network.NetworkException
import com.example.myapplication.network.WeatherNetworkService
import com.example.myapplication.network.await
import com.example.myapplication.ui.weather.data.model.CurrentWeatherResponseModel
import com.example.myapplication.ui.weather.data.model.WeatherForecastResponseModel
import retrofit2.Call
import retrofit2.Retrofit

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */
class WeatherRepository(val retrofit: Retrofit) {
    private val retrofitService: WeatherNetworkService by lazy {
        retrofit.create(WeatherNetworkService::class.java)
    }

    @Throws(RepositoryException.CurrentWeatherException::class)
    suspend fun fetchCurrentWeather(cityName: String): CurrentWeatherResponseModel? {
        return launchAPI({
            return@launchAPI retrofitService.getCurrentWeather(cityName)
        }, { e -> RepositoryException.CurrentWeatherException(e.errorResponseModel) })
    }

    @Throws(RepositoryException.WeatherForecastException::class)
    suspend fun fetchWeatherForecast(
        latitude: Double,
        longitude: Double
    ): WeatherForecastResponseModel? {
        return launchAPI({
            return@launchAPI retrofitService.getForecastWeather(latitude, longitude)
        }, { e -> RepositoryException.CurrentWeatherException(e.errorResponseModel) })
    }


    private suspend fun <T : Any> launchAPI(
        apiCall: () -> Call<T>,
        exception: (e: NetworkException) -> RepositoryException
    ): T? {
        try {
            return apiCall().await()
        } catch (e: NetworkException) {
            throw exception(e)
        }
    }

    sealed class RepositoryException(val errorResponseModel: ErrorResponseModel) :
        Throwable(errorResponseModel.message) {
        class CurrentWeatherException(errorResponseModel: ErrorResponseModel) :
            RepositoryException(errorResponseModel)

        class WeatherForecastException(errorResponseModel: ErrorResponseModel) :
            RepositoryException(errorResponseModel)

        class GeneralException(errorResponseModel: ErrorResponseModel) : RepositoryException(errorResponseModel)
    }
}