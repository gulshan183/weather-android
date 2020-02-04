package com.example.myapplication.ui.main.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.network.RetrofitProvider
import com.example.myapplication.network.WeatherNetworkService
import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //setup navigation
        bt_step1.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_currentWeatherFragment))
        bt_step2.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_forecastWeatherFragment))
    }

}
