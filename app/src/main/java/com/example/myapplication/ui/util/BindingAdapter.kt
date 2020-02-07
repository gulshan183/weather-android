package com.example.myapplication.ui.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.util.BindableAdapter
import com.squareup.picasso.Picasso

/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */

@BindingAdapter("visible")
fun setVisibility(view: View, visible: Boolean) {
    with(view) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }
}

@BindingAdapter("list")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, items: List<T>?) {
    if (recyclerView.adapter is BindableAdapter<*> && items != null) {
        (recyclerView.adapter as BindableAdapter<T>).setData(items)
    }
}

@BindingAdapter("weather_icon")
fun setWeatherIcon(imageView: ImageView, iconCode: String?) {
    Picasso.get()
        .load(if (iconCode != null) "http://openweathermap.org/img/w/$iconCode.png" else null)
        .placeholder(R.drawable.ic_weather_placeholder)
        .into(imageView)
}