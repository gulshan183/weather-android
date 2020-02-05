package com.example.myapplication.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.CurrentWeatherFragmentBinding
import com.example.myapplication.util.obtainViewModel
import com.example.myapplication.util.onSubmit

class CurrentWeatherFragment : Fragment() {

    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var mBinding: CurrentWeatherFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = CurrentWeatherFragmentBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = obtainViewModel(CurrentWeatherViewModel::class.java,requireActivity().application)
        mBinding.viewModel = viewModel
        initView()
        initObservers()
    }

    private fun initView() {
        mBinding.etSearch.onSubmit {
            try {
                viewModel.fetchWeatherForecastForCities(mBinding.etSearch.text.toString())
                mBinding.tiSearch.error = null
            } catch (e: IllegalArgumentException) {
                mBinding.tiSearch.error = e.message
            }
        }
        with(mBinding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = CitiesWeatherAdapter()
        }
    }


    private fun initObservers() {
        viewModel.run {
            toastMessage.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

}
