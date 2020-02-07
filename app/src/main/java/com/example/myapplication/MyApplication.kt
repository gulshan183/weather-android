package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.AppComponent
import com.example.myapplication.di.DaggerAppComponent

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */
open class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }


    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.create()
    }

}