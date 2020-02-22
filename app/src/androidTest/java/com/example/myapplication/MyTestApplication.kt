package com.example.myapplication

import com.example.myapplication.di.AppComponent
import com.example.myapplication.di.DaggerAppComponent
import com.example.myapplication.di.DaggerTestAppComponent


/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */

/**
 * This Application class will used for UI tests
 */
class MyTestApplication : MyApplication() {
    /*
    Intializing TestAppComponent for dependency injection
     */
    override fun initializeComponent(): AppComponent {
        return DaggerTestAppComponent.builder().application(this).build()
    }
}