package com.example.myapplication.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.R
import kotlinx.android.synthetic.main.main_fragment.*

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
