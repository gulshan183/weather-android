package com.example.myapplication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.ui.weather.current.CurrentWeatherViewModel
import com.example.myapplication.ui.weather.forecast.ForecastWeatherViewModel
import com.example.myapplication.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Gulshan Ahluwalia on 2020-02-21.
 */

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CurrentWeatherViewModel::class)
    abstract fun bindCurrentWeatherViewModel(currentWeatherViewModel: CurrentWeatherViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForecastWeatherViewModel::class)
    abstract fun bindForecastWeatherViewModel(forecastWeatherViewModel: ForecastWeatherViewModel):ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory):ViewModelProvider.Factory
}