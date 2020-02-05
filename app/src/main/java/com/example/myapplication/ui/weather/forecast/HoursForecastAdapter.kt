package com.example.myapplication.ui.weather.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.HoursForecastListItemBinding

/**
 * Created by Gulshan Ahluwalia on 2020-02-06.
 */
class HoursForecastAdapter(val hoursWeatherList: List<WeatherTime>) :
    RecyclerView.Adapter<HoursForecastAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HoursForecastListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.item = hoursWeatherList[position]
    }


    inner class ViewHolder(val mBinding: HoursForecastListItemBinding) :
        RecyclerView.ViewHolder(mBinding.root)

    override fun getItemCount(): Int {
        return hoursWeatherList.size
    }
}