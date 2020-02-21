package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.MyApplication
import com.example.myapplication.ui.weather.current.CurrentWeatherFragment
import com.example.myapplication.ui.weather.forecast.ForecastWeatherFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */
@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class,ViewModelModule::class])
interface AppComponent {
    fun inject(weatherFragment: CurrentWeatherFragment)
    fun inject(forecastWeatherFragment: ForecastWeatherFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application):Builder
        fun build():AppComponent
    }
}