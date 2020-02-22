package com.example.myapplication.util

/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */

/**
 * Adapter to be implemented, such that list updates from Data binding could be received.
 *
 */
interface BindableAdapter<T> {
    fun setData(items: List<T>)
}