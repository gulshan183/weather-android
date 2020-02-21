package com.example.myapplication.ui.weather.forecast

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.ui.weather.data.NetworkWeatherRepository
import com.example.myapplication.ui.weather.data.WeatherRepository
import com.example.myapplication.ui.weather.data.model.ListElement
import com.example.myapplication.ui.weather.data.model.WeatherForecastResponseModel
import com.example.myapplication.util.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForecastWeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val context: Application
) : AndroidViewModel(context) {

    private val _textMessage = MutableLiveData<String>()

    val textNotify: LiveData<String>
        get() = _textMessage

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
        launchDataLoad {
            val list = transformModel(
                repository.fetchWeatherForecast(
                    location.latitude,
                    location.longitude
                )
            )
            _forecastList.value = list
            if (list.isEmpty()) {
                _textMessage.value = context.getString(R.string.no_data_found)
            }

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
                        getMinMaxDegreeFormat(
                            it.main?.tempMax?.toString(),
                            it.main?.tempMin?.toString()
                        ) ?: context.getString(R.string.unavailable),
                        windSpeed = it.wind?.speed?.toString()?.toWindFormat() ?: context.getString(
                            R.string.unavailable
                        ),
                        description = it.weather?.getOrNull(0)?.description?.toTitleCase()
                            ?: context.getString(R.string.unavailable),
                        time = getTime(it.dt) ?: context.getString(R.string.unavailable),
                        iconCode = it.weather?.getOrNull(0)?.icon
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

    private fun launchDataLoad(
        loadingLiveData: MutableLiveData<Boolean> = _loadingState,
        block: suspend () -> Unit
    ): Job {
        loadingLiveData.value = true
        return viewModelScope.launch {
            try {
                block()
            } catch (e: NetworkWeatherRepository.RepositoryException) {
                _textMessage.value = if (e.errorResponseModel.msgRes != null) {
                    context.getString(e.errorResponseModel.msgRes)
                } else null
            } finally {
                loadingLiveData.value = false
            }
        }

    }

    fun showLoader() {
        _loadingState.value = true
    }
}
