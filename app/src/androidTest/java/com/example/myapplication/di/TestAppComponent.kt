package com.example.myapplication.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */

/**
 * TestAppComponent for the UI Tests. This component provides access to fake repository which
 * will be injected to the ViewModels for UI Tests only
 */
@Singleton
@Component(modules = [NetworkModule::class, TestRepositoryModule::class, ViewModelModule::class])
interface TestAppComponent : AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}