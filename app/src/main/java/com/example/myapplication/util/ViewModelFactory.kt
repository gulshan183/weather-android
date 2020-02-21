package com.example.myapplication.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */

class ViewModelFactory @Inject constructor(
    private val creaters:Map<Class<out ViewModel>,  @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creater = creaters[modelClass] ?: creaters.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value
        ?: throw IllegalArgumentException("${modelClass.simpleName} view model is unknown")
        @Suppress("UNCHECKED_CAST")
        return creater.get() as T
    }
}