package com.example.myapplication.util

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>,context: Application): T {
    val repository = ServiceLocator.provideRepository()
    return ViewModelProvider(this, ViewModelFactory(repository,context)).get(viewModelClass)
}