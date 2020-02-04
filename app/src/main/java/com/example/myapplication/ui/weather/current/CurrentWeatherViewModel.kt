package com.example.myapplication.ui.weather.current

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.R
import com.example.myapplication.network.ErrorResponseModel
import com.example.myapplication.ui.weather.data.WeatherRepository
import kotlinx.coroutines.*

class CurrentWeatherViewModel(
    private val context: Application,
    private val repository: WeatherRepository
) : AndroidViewModel(context) {
    private val _toastMessage = MutableLiveData<String>()

    val toastMessage: LiveData<String>
        get() = _toastMessage

    private val _userList = MutableLiveData<ArrayList<Void>>()

    val userList: LiveData<ArrayList<Void>>
        get() = _userList

    private val _loadingState = MutableLiveData<Boolean>(false)

    val isLoading: LiveData<Boolean>
        get() = _loadingState

    fun fetchWeatherForecast(cityName: Array<String> = arrayOf("london", "jalandhar", "New york")) {
        launchDataLoad { asyncScope ->
            asyncScope.run {
                val exceptionCities = ArrayList<String>()
                val deferreds = cityName.map {
                    async {
                        try {
                            repository.fetchCurrentWeather(it)
                        } catch (e: WeatherRepository.RepositoryException.CurrentWeatherException) {
                            exceptionCities.add(it)
                            null
                        }
                    }
                }
                val a = deferreds.awaitAll().filterNotNull()
                if(exceptionCities.size>0) {
                    throwExceptionForWeatherAPI(exceptionCities)
                }
            }

        }
    }

    private fun throwExceptionForWeatherAPI(exceptionCities: ArrayList<String>) {
        throw WeatherRepository.RepositoryException.GeneralException(
            ErrorResponseModel(
                message =
                context.getString(
                    R.string.weather_city_error,
                    exceptionCities.reduceIndexed { pos, acc, s ->
                        if (pos == exceptionCities.size - 1) "$acc and $s" else "$acc, $s"
                    })
            )
        )
    }

    private fun launchDataLoad(
        loadingLiveData: MutableLiveData<Boolean> = _loadingState,
        block: suspend (coroutineScope: CoroutineScope) -> Unit
    ): Job {
        loadingLiveData.value = true
        return viewModelScope.launch {
            try {
                block(this)
            } catch (e: WeatherRepository.RepositoryException) {
                _toastMessage.value = if (e.errorResponseModel.msgRes != null) {
                    context.getString(e.errorResponseModel.msgRes)
                } else {
                    e.message
                }
            } finally {
                loadingLiveData.value = false
            }
        }

    }
}
