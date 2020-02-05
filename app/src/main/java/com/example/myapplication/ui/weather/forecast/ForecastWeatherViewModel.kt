package com.example.myapplication.ui.weather.forecast

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.weather.data.WeatherRepository
import com.example.myapplication.ui.weather.data.model.ListElement
import com.example.myapplication.ui.weather.data.model.WeatherForecastResponseModel
import com.example.myapplication.util.getDateInUIFormat
import com.example.myapplication.util.getTime
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ForecastWeatherViewModel(
    private val repository: WeatherRepository,
    private val context: Application
) : AndroidViewModel(context) {

    private val _toastMessage = MutableLiveData<Int>()

    val toastMessage: LiveData<Int>
        get() = _toastMessage

    private val _forecastList = MutableLiveData<List<ForecastWeatherModel>>()

    val forecastList: LiveData<List<ForecastWeatherModel>>
        get() = _forecastList

    private val _city = MutableLiveData<String>()

    val city: LiveData<String>
        get() = _city

    private val _loadingState = MutableLiveData<Boolean>(false)

    val isLoading: LiveData<Boolean>
        get() = _loadingState

    fun fetchWeatherForecast(location: Location) {
        launchDataLoad{
            _forecastList.value = transformModel(
                repository.fetchWeatherForecast(
                    location.latitude,
                    location.longitude
                )
            )
        }
    }

    private fun transformModel(weatherForecastResponse: WeatherForecastResponseModel?): List<ForecastWeatherModel> {
        _city.value = weatherForecastResponse?.city?.name
        val map = groupSameDays(weatherForecastResponse?.list)
        return getForecastModels(map)
    }

    private fun getForecastModels(dateMap: Map<String, List<ListElement>>): List<ForecastWeatherModel> {
        return dateMap.map { entry ->
            ForecastWeatherModel(
                weatherHours = entry.value.map {
                    WeatherTime(
                        maxTemp = it.main?.tempMax?.toString(),
                        minTemp = it.main?.tempMin?.toString(),
                        windSpeed = it.wind?.speed?.toString(),
                        description = it.weather?.getOrNull(0)?.description,
                        time = getTime(it.dt)
                    )
                },
                date = entry.key
            )
        }
    }

    private fun groupSameDays(list: List<ListElement>?): Map<String, List<ListElement>> {
        return list?.groupBy {
            getDateInUIFormat(it.dt ?: 0, context)
        } ?: emptyMap()
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

    fun showLoader() {
        _loadingState.value = true
    }
}
