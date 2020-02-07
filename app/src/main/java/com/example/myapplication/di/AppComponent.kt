package com.example.myapplication.di

import com.example.myapplication.ui.weather.current.CurrentWeatherFragment
import com.example.myapplication.ui.weather.forecast.ForecastWeatherFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */
@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(weatherFragment: CurrentWeatherFragment)
    fun inject(forecastWeatherFragment: ForecastWeatherFragment)
}