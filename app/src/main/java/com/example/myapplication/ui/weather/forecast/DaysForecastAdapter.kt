package com.example.myapplication.ui.weather.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.DaysForecastListItemBinding
import com.example.myapplication.util.BindableAdapter

/**
 * Created by Gulshan Ahluwalia on 2020-02-06.
 */
class DaysForecastAdapter : RecyclerView.Adapter<DaysForecastAdapter.ViewHolder>(),
    BindableAdapter<ForecastWeatherModel> {

    private var dayList = emptyList<ForecastWeatherModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DaysForecastListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dayList[position])
    }

    override fun setData(items: List<ForecastWeatherModel>) {
        dayList = items
        notifyItemRangeInserted(0, itemCount)
    }


    inner class ViewHolder(private val binding: DaysForecastListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ForecastWeatherModel) {
            binding.list.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = HoursForecastAdapter(item.weatherHours ?: emptyList())


            }

            binding.item = item

            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}