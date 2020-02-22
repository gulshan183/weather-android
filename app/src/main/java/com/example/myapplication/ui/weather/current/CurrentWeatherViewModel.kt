package com.example.myapplication.ui.weather.current

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.network.ErrorResponseModel
import com.example.myapplication.network.NOT_FOUND
import com.example.myapplication.network.NO_INTERNET
import com.example.myapplication.ui.weather.data.NetworkWeatherRepository
import com.example.myapplication.ui.weather.data.WeatherRepository
import com.example.myapplication.ui.weather.data.model.CurrentWeatherResponseModel
import com.example.myapplication.util.getMinMaxDegreeFormat
import com.example.myapplication.util.toDegreeFormat
import com.example.myapplication.util.toTitleCase
import com.example.myapplication.util.toWindFormat
import kotlinx.coroutines.*
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(
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

    /**
     * Fetch the weather for the city names. It creates the [CoroutineScope.async] for each city.
     * The list of [CoroutineScope.async] is created for the cities. Later [awaitAll] is used to
     * **await** for each response.
     *
     * Any error in the individual response is added to the list of exceptions. Which is later used
     * to update the UI.
     *
     *
     * @param cityNames List of city names
     */
    private fun fetchWeatherForecast(cityNames: List<String>) {
        launchDataLoad { asyncScope ->
            asyncScope.run {
                val exceptionCities = ArrayList<ErrorModel>()
                val deferreds = cityNames.map {
                    async {
                        try {
                            repository.fetchCurrentWeather(it)
                        } catch (e: NetworkWeatherRepository.RepositoryException.CurrentWeatherException) {
                            exceptionCities.add(ErrorModel(it, e.errorResponseModel))
                            null
                        }
                    }
                }
                _cityWeatherList.value = transformModel(deferreds.awaitAll().filterNotNull())
                if (exceptionCities.size > 0) {
                    throwExceptionForWeatherAPI(exceptionCities)
                }
            }

        }
    }

    /**
     * Transforms the response from API to a model which will be used to update UI
     *
     * @param responseList
     * @return [List] of [CurrentWeatherModel]
     */
    private fun transformModel(responseList: List<CurrentWeatherResponseModel>): List<CurrentWeatherModel> {
        return responseList.map {
            CurrentWeatherModel(
                cityId = it.id ?: 0,
                cityName = it.name ?: context.getString(R.string.unavailable),
                temp = it.main?.temp?.toString()?.toDegreeFormat()
                    ?: context.getString(R.string.unavailable),
                maxMinTemp = getMinMaxDegreeFormat(
                    it.main?.tempMax?.toString(),
                    it.main?.tempMin?.toString()
                ) ?: context.getString(R.string.unavailable),
                windSpeed = it.wind?.speed?.toString()?.toWindFormat()
                    ?: context.getString(R.string.unavailable),
                description = it.weather?.getOrNull(0)?.description?.toTitleCase()
                    ?: context.getString(R.string.unavailable),
                iconCode = it.weather?.getOrNull(0)?.icon

            )
        }.distinctBy { it.cityId }
    }


    /**
     * Handle exceptions(to update UI) thrown by each API call.
     *
     * @param exceptionCities
     */
    private fun throwExceptionForWeatherAPI(exceptionCities: ArrayList<ErrorModel>) {
        val notFoundList = ArrayList<String>()
        val noInternetList = ArrayList<String>()
        val generalList = ArrayList<String>()
        exceptionCities.forEach {
            when (it.errorResponseModel.errorCode) {
                NO_INTERNET -> noInternetList.add(it.city)
                NOT_FOUND -> notFoundList.add(it.city)
                else -> generalList.add(it.city)
            }
        }
        val errorString = getErrorMessage(notFoundList, R.string.weather_city_not_found) +
                "\n" + getErrorMessage(noInternetList, R.string.weather_city_no_internet) +
                "\n" + getErrorMessage(generalList, R.string.weather_city_error)

        if (errorString.isNotEmpty()) {
            throw NetworkWeatherRepository.RepositoryException.GeneralException(
                ErrorResponseModel(
                    message =
                    errorString.trim()
                )
            )
        }
    }

    private fun getErrorMessage(errorCities: List<String>, msgResId: Int): String {
        return if (errorCities.isNotEmpty()) {
            context.getString(
                msgResId,
                errorCities.reduceIndexed { pos, acc, s ->
                    if (pos == errorCities.size - 1) "$acc and $s" else "$acc, $s"
                })
        } else ""
    }

    /**
     * Handles the [CoroutineScope] with respect to the lifecycle of [AndroidViewModel].
     * Also maintains the loading state of UI. Which would otherwise to become boilerplate code.
     *
     * @param loadingLiveData
     * @param block
     * @return [Job] which is cancellable
     */
    private fun launchDataLoad(
        loadingLiveData: MutableLiveData<Boolean> = _loadingState,
        block: suspend (coroutineScope: CoroutineScope) -> Unit
    ): Job {
        loadingLiveData.value = true
        return viewModelScope.launch {
            try {
                block(this)
            } catch (e: NetworkWeatherRepository.RepositoryException) {
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

    /**
     * Take cities, handle their validations and proceed to fetch their weather data.
     * @throws [IllegalArgumentException] if the cities are not in the valid format or as per
     * requirements.
     *
     * @param citiesCSV Comma seperated string of the cities
     */
    @Throws(java.lang.IllegalArgumentException::class)
    fun fetchWeatherForecastForCities(citiesCSV: String) {
        val cities = citiesCSV.split(",").filter { it.trim().isNotEmpty() }.distinct()
        when {
            cities.size < 3 -> {
                throw IllegalArgumentException(context.getString(R.string.cities_min_error))
            }
            cities.size > 7 -> {
                throw IllegalArgumentException(context.getString(R.string.cities_max_error))
            }
            else -> {
                fetchWeatherForecast(cities)
            }
        }
    }

    data class ErrorModel(val city: String, val errorResponseModel: ErrorResponseModel)
}


