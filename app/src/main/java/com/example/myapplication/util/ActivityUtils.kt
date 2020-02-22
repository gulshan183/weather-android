package com.example.myapplication.util

import android.app.Activity
import com.example.myapplication.MyApplication
import com.example.myapplication.di.AppComponent

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */

/**
 * Get the Dagger [AppComponent] for dependency injections. Extension of Activity.
 *
 * @return [AppComponent]
 */
fun Activity.getAppComponent(): AppComponent? {
    return (applicationContext as? MyApplication)?.appComponent
}