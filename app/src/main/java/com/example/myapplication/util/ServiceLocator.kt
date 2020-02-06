package com.example.myapplication.util

import androidx.annotation.VisibleForTesting
import com.example.myapplication.network.RetrofitProvider
import com.example.myapplication.ui.weather.data.NetworkWeatherRepository
import com.example.myapplication.ui.weather.data.WeatherRepository

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */
object ServiceLocator {
    var repository: WeatherRepository? = null
    @VisibleForTesting set

    fun provideRepository(): WeatherRepository {
        return repository ?: NetworkWeatherRepository(RetrofitProvider.retrofitInstance)
    }
}