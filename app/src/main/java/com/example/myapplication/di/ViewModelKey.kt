package com.example.myapplication.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by Gulshan Ahluwalia on 2020-02-21.
 */

/**
 * Provides the key which is the [KClass] of ViewModel. This will be later used while creating [Map]
 * which will be further provided to the factory of view model
 *
 * @property value
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)