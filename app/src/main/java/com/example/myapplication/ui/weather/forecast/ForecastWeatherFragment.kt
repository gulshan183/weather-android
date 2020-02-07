package com.example.myapplication.ui.weather.forecast

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ForecastWeatherFragmentBinding
import com.example.myapplication.ui.util.PermissionAwareFragment
import com.example.myapplication.ui.weather.data.WeatherRepository
import com.example.myapplication.util.*
import javax.inject.Inject


class ForecastWeatherFragment : PermissionAwareFragment() {

    private lateinit var viewModel: ForecastWeatherViewModel
    private lateinit var locationProvider: LocationProvider
    private lateinit var mBinding: ForecastWeatherFragmentBinding

    @Inject
    lateinit var weatherRepository: WeatherRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = ForecastWeatherFragmentBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onPermissionGranted() {
        observerCurrentLocation()
    }

    private fun observerCurrentLocation() {
        viewModel.showLoader()
        locationProvider.requestLocationUpdates(object : LocationUpdateCallback {
            override fun onLocationUpdated(location: Location) {
                viewModel.fetchWeatherForecast(location)
                locationProvider.stopLocationUpdates()
            }

        })
    }


    override fun onPermissionNotGranted() {
        findNavController().navigateUp()
        Toast.makeText(context, R.string.location_permission_required, Toast.LENGTH_LONG).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = obtainViewModel(
            ForecastWeatherViewModel::class.java,
            requireActivity().application,
            weatherRepository
        )
        mBinding.viewModel = viewModel
        initObservers()
        initView()
    }

    private fun initView() {
        locationProvider = LocationProvider(requireActivity(), lifecycle)
        lifecycle.addObserver(locationProvider)


        with(mBinding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = DaysForecastAdapter()
        }

        mBinding.ivRetry.setOnClickListener {
            observerCurrentLocation()
        }
    }

    override fun onStart() {
        super.onStart()
        requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun initObservers() {
        viewModel.run {
            city.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    (activity as? AppCompatActivity?)?.supportActionBar?.title = it
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOCATION_SETTING_REQUEST || resultCode == GOOGLE_SERVICE_RESOLVE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                observerCurrentLocation()
            } else {
                onPermissionNotGranted()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().getAppComponent()?.inject(this)
    }

}
