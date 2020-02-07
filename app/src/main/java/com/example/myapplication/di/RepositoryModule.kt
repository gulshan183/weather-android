package com.example.myapplication.di

import com.example.myapplication.ui.weather.data.NetworkWeatherRepository
import com.example.myapplication.ui.weather.data.WeatherRepository
import dagger.Binds
import dagger.Module

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository: NetworkWeatherRepository): WeatherRepository
}