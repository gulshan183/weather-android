package com.example.myapplication.di

import com.example.myapplication.ui.weather.data.FakeWeatherRepository
import com.example.myapplication.ui.weather.data.WeatherRepository
import dagger.Binds
import dagger.Module

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */
@Module
abstract class TestRepositoryModule {

    /**
     * Provides mocked [WeatherRepository].
     *
     * @param repository
     * @return [WeatherRepository]
     */
    @Binds
    abstract fun provideRepository(repository: FakeWeatherRepository): WeatherRepository
}