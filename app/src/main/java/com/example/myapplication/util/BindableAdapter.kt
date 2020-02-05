package com.example.myapplication.util

/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */
interface BindableAdapter<T> {
    fun setData(items: List<T>)
}