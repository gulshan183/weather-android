package com.example.myapplication.ui.weather.forecast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.weather.data.WeatherRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ForecastWeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _toastMessage = MutableLiveData<Int>()

    val toastMessage: LiveData<Int>
        get() = _toastMessage

    private val _userList = MutableLiveData<ArrayList<Void>>()

    val userList: LiveData<ArrayList<Void>>
        get() = _userList

    private val _loadingState = MutableLiveData<Boolean>(false)

    val isLoading: LiveData<Boolean>
        get() = _loadingState

    fun fetchWeatherForecast(cityName:String="london"){
        launchDataLoad{
            val a=repository.fetchWeatherForecast(cityName)
            Log.d("das",a.toString());

        }
    }

    private fun launchDataLoad(loadingLiveData: MutableLiveData<Boolean> = _loadingState,
                               block: suspend () -> Unit): Job {
        loadingLiveData.value = true
        return viewModelScope.launch {
            try {
                block()
            } catch (e: WeatherRepository.RepositoryException) {
                _toastMessage.value = e.errorResponseModel.msgRes
            } finally {
                loadingLiveData.value = false
            }
        }

    }
}
