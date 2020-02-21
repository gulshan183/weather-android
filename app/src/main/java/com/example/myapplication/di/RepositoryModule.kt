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

    /**
     * Binds [NetworkWeatherRepository] to [WeatherRepository]
     * This is will be injected in view model constructor.
     *
     * @param repository
     * @return [WeatherRepository]
     */
    @Binds
    abstract fun provideRepository(repository: NetworkWeatherRepository): WeatherRepository
}