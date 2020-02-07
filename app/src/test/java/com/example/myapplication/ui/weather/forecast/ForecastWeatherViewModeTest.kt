package com.example.myapplication.ui.weather.forecast

import android.app.Application
import android.location.Location
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.R
import com.example.myapplication.constant.APIResponseState
import com.example.myapplication.ui.weather.data.FakeWeatherRepository
import com.google.common.truth.Truth
import com.example.myapplication.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Created by Gulshan Ahluwalia on 2020-02-06.
 */
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@ExperimentalCoroutinesApi
class ForecastWeatherViewModeTest {

    private lateinit var viewModel: ForecastWeatherViewModel
    private lateinit var repository: FakeWeatherRepository
    private lateinit var context: Application

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        context = ApplicationProvider.getApplicationContext()
        repository = FakeWeatherRepository()
        viewModel = ForecastWeatherViewModel(repository, context)
    }

    @Test
    fun showLoader() {
        viewModel.showLoader()
        Truth.assertThat(viewModel.isLoading.value).isTrue()
    }

    @Test
    fun fetchWeatherForecastForCities_Loading() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.fetchWeatherForecast(Location("dummyProvider").apply {
            latitude = 31.5
            longitude = 65.3
        })
        Truth.assertThat(viewModel.isLoading.value).isEqualTo(true)
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(viewModel.isLoading.value).isEqualTo(false)

    }

    @Test
    fun fetchWeatherForecastForCities_Success() {
        viewModel.fetchWeatherForecast(Location("dummyProvider").apply {
            latitude = 31.5
            longitude = 65.3
        })
        Truth.assertThat(viewModel.forecastList.value?.size).isEqualTo(2)

    }

    @Test
    fun fetchWeatherForecastForCities_validateCity() {
        viewModel.fetchWeatherForecast(Location("dummyProvider").apply {
            latitude = 31.5
            longitude = 65.3
        })
        Truth.assertThat(viewModel.city.value).isEqualTo("London")
    }

    @Test
    fun fetchWeatherForecastForCities_validateDate() {
        viewModel.fetchWeatherForecast(Location("dummyProvider").apply {
            latitude = 31.5
            longitude = 65.3
        })
        Truth.assertThat(viewModel.forecastList.value?.getOrNull(0)?.date).isEqualTo(
            context.getString(
                R.string.today
            )
        )
        Truth.assertThat(viewModel.forecastList.value?.getOrNull(1)?.date).isEqualTo(
            context.getString(
                R.string.tomorrow
            )
        )
    }

    @Test
    fun fetchWeatherForecastForCities_notFound() {
        repository.apiResponseState = APIResponseState.NOT_FOUND
        viewModel.fetchWeatherForecast(Location("dummyProvider").apply {
            latitude = 31.5
            longitude = 65.3
        })
        Truth.assertThat(viewModel.forecastList.value?.size).isEqualTo(0)
        Truth.assertThat(viewModel.textNotify.value)
            .isEqualTo(context.getString(R.string.no_data_found))
    }

    @Test
    fun fetchWeatherForecastForCities_errorNetwork() {
        repository.apiResponseState = APIResponseState.NETWORK_ERROR
        viewModel.fetchWeatherForecast(Location("dummyProvider").apply {
            latitude = 31.5
            longitude = 65.3
        })
        Truth.assertThat(viewModel.forecastList.value?.size).isEqualTo(null)
        Truth.assertThat(viewModel.textNotify.value)
            .isEqualTo(context.getString(R.string.unreachable_server_internet_error))
    }

    @Test
    fun fetchWeatherForecastForCities_errorAPIKey() {
        repository.apiResponseState = APIResponseState.API_KEY_ERROR
        viewModel.fetchWeatherForecast(Location("dummyProvider").apply {
            latitude = 31.5
            longitude = 65.3
        })
        Truth.assertThat(viewModel.forecastList.value?.size).isEqualTo(null)
        Truth.assertThat(viewModel.textNotify.value)
            .isEqualTo(context.getString(R.string.something_went_wrong))
    }


}