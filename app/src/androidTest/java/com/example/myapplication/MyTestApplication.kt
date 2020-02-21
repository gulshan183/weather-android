package com.example.myapplication

import com.example.myapplication.di.AppComponent
import com.example.myapplication.di.DaggerAppComponent
import com.example.myapplication.di.DaggerTestAppComponent


/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */
class MyTestApplication : MyApplication() {
    override fun initializeComponent(): AppComponent {
        return DaggerTestAppComponent.builder().application(this).build()
    }
}