package com.example.myapplication.ui.weather.current

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CurrentWeatherListItemBinding
import com.example.myapplication.util.BindableAdapter
import okhttp3.internal.toImmutableList

/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */
class CitiesWeatherAdapter
    : ListAdapter<CurrentWeatherModel, CitiesWeatherAdapter.ViewHolder>(DIFF_CALLBACK),
    BindableAdapter<CurrentWeatherModel> {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CitiesWeatherAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CurrentWeatherListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CitiesWeatherAdapter.ViewHolder, position: Int) {
        holder.mBinding.item = getItem(position)
    }

    override fun setData(items: List<CurrentWeatherModel>) {
        submitList(items.toImmutableList())
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CurrentWeatherModel>() {
            override fun areItemsTheSame(
                oldItem: CurrentWeatherModel,
                newItem: CurrentWeatherModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CurrentWeatherModel,
                newItem: CurrentWeatherModel
            ): Boolean {
                return oldItem.areContentsTheSame(newItem)
            }

        }
    }

    inner class ViewHolder(val mBinding: CurrentWeatherListItemBinding) :
        RecyclerView.ViewHolder(mBinding.root)
}