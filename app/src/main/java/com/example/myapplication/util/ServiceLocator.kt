package com.example.myapplication.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.myapplication.network.RetrofitProvider
import com.example.myapplication.ui.weather.data.WeatherRepository

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */
object ServiceLocator {
    var repository :WeatherRepository? = null
    @VisibleForTesting set

    fun provideRepository(): WeatherRepository {
            return repository  ?: WeatherRepository(RetrofitProvider.retrofitInstance)
    }
}