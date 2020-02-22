package com.example.myapplication.ui.weather.data

import com.example.myapplication.network.ErrorResponseModel
import com.example.myapplication.network.NetworkException
import com.example.myapplication.network.WeatherNetworkService
import com.example.myapplication.network.await
import com.example.myapplication.ui.weather.data.model.CurrentWeatherResponseModel
import com.example.myapplication.ui.weather.data.model.WeatherForecastResponseModel
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */

class NetworkWeatherRepository
@Inject constructor(private val retrofit: Retrofit) : WeatherRepository {
    private val retrofitService: WeatherNetworkService by lazy {
        retrofit.create(WeatherNetworkService::class.java)
    }

    /**
     * Fetch the weather for the city.
     * @throws [RepositoryException.CurrentWeatherException] for any response error from API.
     * @param cityName
     */
    @Throws(RepositoryException.CurrentWeatherException::class)
    override suspend fun fetchCurrentWeather(cityName: String): CurrentWeatherResponseModel? {
        return launchAPI({
            return@launchAPI retrofitService.getCurrentWeather(cityName)
        }, { e -> RepositoryException.CurrentWeatherException(e.errorResponseModel) })
    }

    /**
     * Fetch the weather for the specified location.
     * @throws [RepositoryException.WeatherForecastException] for any response error from API.
     * @param latitude
     * @param longitude
     */
    @Throws(RepositoryException.WeatherForecastException::class)
    override suspend fun fetchWeatherForecast(
        latitude: Double,
        longitude: Double
    ): WeatherForecastResponseModel? {
        return launchAPI({
            return@launchAPI retrofitService.getForecastWeather(latitude, longitude)
        }, { e -> RepositoryException.WeatherForecastException(e.errorResponseModel) })
    }


    /**
     * Genric method to accomodate all kind network requests called.
     *
     * @param apiCall
     * @param exception
     */
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

    // Exceptions related to data requests.
    sealed class RepositoryException(val errorResponseModel: ErrorResponseModel) :
        Throwable(errorResponseModel.message) {
        class CurrentWeatherException(errorResponseModel: ErrorResponseModel) :
            RepositoryException(errorResponseModel)

        class WeatherForecastException(errorResponseModel: ErrorResponseModel) :
            RepositoryException(errorResponseModel)

        class GeneralException(errorResponseModel: ErrorResponseModel) :
            RepositoryException(errorResponseModel)
    }
}