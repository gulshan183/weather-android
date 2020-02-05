package com.example.myapplication.ui.weather.current

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.network.ErrorResponseModel
import com.example.myapplication.ui.weather.data.WeatherRepository
import com.example.myapplication.ui.weather.data.model.CurrentWeatherResponseModel
import kotlinx.coroutines.*

class CurrentWeatherViewModel(
    private val context: Application,
    private val repository: WeatherRepository
) : AndroidViewModel(context) {
    private val _toastMessage = MutableLiveData<String>()

    val toastMessage: LiveData<String>
        get() = _toastMessage

    private val _cityWeatherList = MutableLiveData<List<CurrentWeatherModel>>()

    val cityWeatherList: LiveData<List<CurrentWeatherModel>>
        get() = _cityWeatherList

    private val _loadingState = MutableLiveData<Boolean>(false)

    val isLoading: LiveData<Boolean>
        get() = _loadingState

    fun fetchWeatherForecast(cityName: List<String>) {
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
                _cityWeatherList.value = transformModel(deferreds.awaitAll().filterNotNull())
                if(exceptionCities.size>0) {
                    throwExceptionForWeatherAPI(exceptionCities)
                }
            }

        }
    }

    private fun transformModel(responseList: List<CurrentWeatherResponseModel>): List<CurrentWeatherModel> {
        return responseList.map {
            CurrentWeatherModel(
                cityId = it.id ?: 0,
                cityName = it.name,
                maxTemp = it.main?.tempMax?.toString(),
                minTemp = it.main?.tempMin?.toString(),
                windSpeed = it.wind?.speed?.toString(),
                description = it.weather?.getOrNull(0)?.description
            )
        }.distinctBy { it.cityId }
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

    fun fetchWeatherForecastForCities(citiesCSV: String) {
        val cities = citiesCSV.split(",").map { it.trim() }.distinct()
        if (cities.size < 3) {
            throw IllegalArgumentException(context.getString(R.string.cities_min_error))
        } else if (cities.size > 7) {
            throw IllegalArgumentException(context.getString(R.string.cities_max_error))
        } else {
            fetchWeatherForecast(cities)
        }
    }
}
