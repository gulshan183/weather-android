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

/**
 * Set the visibility of the view to either [View.VISIBLE] or [View.GONE]
 *
 * @param view
 * @param visible true to show the view, false otherwise
 */
@BindingAdapter("visible")
fun setVisibility(view: View, visible: Boolean) {
    with(view) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }
}

/**
 * Custom binding adapter for binding the list to the [RecyclerView.Adapter].
 * The [RecyclerView.Adapter] should implement [BindableAdapter] in order to
 * receive updates to the list
 *
 * @param recyclerView
 * @param items
 *
 * @see [BindingAdapter]
 */
@BindingAdapter("list")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, items: List<T>?) {
    if (recyclerView.adapter is BindableAdapter<*> && items != null) {
        (recyclerView.adapter as BindableAdapter<T>).setData(items)
    }
}

/**
 * Custom binding adapter to render the [ImageView] with the icon specified by icon code.
 * With the use [Picasso] the image is fetched from the Open Weather API. If fails,
 * the placeholder will be rendered.
 *
 * @param imageView
 * @param iconCode
 */
@BindingAdapter("weather_icon")
fun setWeatherIcon(imageView: ImageView, iconCode: String?) {
    Picasso.get()
        .load(if (iconCode != null) "https://openweathermap.org/img/w/$iconCode.png" else null)
        .placeholder(R.drawable.ic_weather_placeholder)
        .into(imageView)
}