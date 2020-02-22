package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.AppComponent
import com.example.myapplication.di.DaggerAppComponent

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */
open class MyApplication : Application() {

    // Dagger main component, with lifecycle scope of application
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    //Initializing AppComponent
    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.builder().application(this).build()
    }

}