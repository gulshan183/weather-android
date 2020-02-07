package com.example.myapplication.util

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.ui.weather.data.WeatherRepository

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */

fun <T : ViewModel> Fragment.obtainViewModel(
    viewModelClass: Class<T>,
    context: Application,
    repository: WeatherRepository
): T {
    return ViewModelProvider(this, ViewModelFactory(repository, context)).get(viewModelClass)
}