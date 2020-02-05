package com.example.myapplication.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.ui.weather.current.CurrentWeatherViewModel
import com.example.myapplication.ui.weather.data.WeatherRepository
import com.example.myapplication.ui.weather.forecast.ForecastWeatherViewModel

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */

class ViewModelFactory constructor(
    private val weatherRepository: WeatherRepository,
    private val context: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(CurrentWeatherViewModel::class.java) ->
                    CurrentWeatherViewModel(context,weatherRepository)
                isAssignableFrom(ForecastWeatherViewModel::class.java) ->
                    ForecastWeatherViewModel(weatherRepository, context)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}