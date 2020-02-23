package com.example.myapplication

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */

/**
 * Custom Runner for providing [MyTestApplication] only for UI tests.
 * Thus, [MyTestApplication] will be available as [Application] under test environment.
 *
 * Look at the implementation of this at build.gradle file
 *
 */
class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, MyTestApplication::class.java.name, context)
    }
}