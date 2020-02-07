package com.example.myapplication.di

import dagger.Component
import javax.inject.Singleton

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */
@Singleton
@Component(modules = [NetworkModule::class, TestRepositoryModule::class])
interface TestAppComponent : AppComponent