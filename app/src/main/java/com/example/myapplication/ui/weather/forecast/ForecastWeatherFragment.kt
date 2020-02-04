package com.example.myapplication.ui.weather.forecast

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.example.myapplication.R
import com.example.myapplication.util.obtainViewModel

class ForecastWeatherFragment : Fragment() {

    private lateinit var viewModel: ForecastWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = obtainViewModel(ForecastWeatherViewModel::class.java,requireActivity().application)
        initObservers()
        initView()
    }

    private fun initView() {
        viewModel.fetchWeatherForecast()
    }

    private fun initObservers() {
        viewModel.run {
            toastMessage.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    Toast.makeText(requireContext(),getString(it),Toast.LENGTH_LONG).show()
                }
            })
        }
    }


}
