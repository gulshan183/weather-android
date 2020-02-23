package com.example.myapplication.ui.weather.current

import android.app.Application
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.R
import com.example.myapplication.constant.APIResponseState
import com.example.myapplication.ui.weather.data.FakeWeatherRepository
import com.example.myapplication.util.MainCoroutineRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
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
class CurrentWeatherViewModelTest {
    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var repository: FakeWeatherRepository
    private lateinit var context: Application

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    /**
     * Init [CurrentWeatherViewModel] before starting test
     *
     */
    @Before
    fun setupViewModel() {
        context = ApplicationProvider.getApplicationContext()
        repository = FakeWeatherRepository()
        viewModel = CurrentWeatherViewModel(context, repository)
    }

    @Test
    fun fetchWeatherForecastForCities_success() {
        viewModel.fetchWeatherForecastForCities("abc,def,ijk,lmn,op")
        Truth.assertThat(viewModel.cityWeatherList.value?.size).isEqualTo(5)
    }

    @Test
    fun fetchWeatherForecastForCities_successDistinctResult() {
        viewModel.fetchWeatherForecastForCities("abc,abc,abc,lmn,op")
        Truth.assertThat(viewModel.cityWeatherList.value?.size).isEqualTo(3)
    }

    @Test
    fun fetchWeatherForecastForCities_successInvalidInput() {
        viewModel.fetchWeatherForecastForCities(",,,a,lmn,op")
        Truth.assertThat(viewModel.cityWeatherList.value?.size).isEqualTo(3)
        Truth.assertThat(viewModel.toastMessage.value).isEqualTo(null)
    }

    @Test
    fun fetchWeatherForecastForCities_errorMinCities() {
        try {
            viewModel.fetchWeatherForecastForCities("lmn,op")
            Assert.fail("Should have thrown exception")
        } catch (e: IllegalArgumentException) {
            Truth.assertThat(e.message).isEqualTo(context.getString(R.string.cities_min_error))
        }
    }

    @Test
    fun fetchWeatherForecastForCities_errorMaxCities() {
        try {
            viewModel.fetchWeatherForecastForCities("lmn,op,dsa,sas,de,das,dsa,dsa,w,dq")
            Assert.fail("Should have thrown exception")
        } catch (e: IllegalArgumentException) {
            Truth.assertThat(e.message).isEqualTo(context.getString(R.string.cities_max_error))
        }
    }

    @Test
    fun fetchWeatherForecastForCities_errorNetwork() {
        repository.apiResponseState = APIResponseState.NETWORK_ERROR
        viewModel.fetchWeatherForecastForCities("a,lmn,op")
        Truth.assertThat(viewModel.cityWeatherList.value?.size).isEqualTo(0)
        Truth.assertThat(viewModel.toastMessage.value)
            .isEqualTo(context.getString(R.string.weather_city_no_internet, "a, lmn and op"))
    }

    @Test
    fun fetchWeatherForecastForCities_errorNotFound() {
        repository.apiResponseState = APIResponseState.NOT_FOUND
        viewModel.fetchWeatherForecastForCities("a,lmn,op")
        Truth.assertThat(viewModel.cityWeatherList.value?.size).isEqualTo(0)
        Truth.assertThat(viewModel.toastMessage.value)
            .isEqualTo(context.getString(R.string.weather_city_not_found, "a, lmn and op"))
    }

    @Test
    fun fetchWeatherForecastForCities_errorAPIError() {
        repository.apiResponseState = APIResponseState.API_KEY_ERROR
        viewModel.fetchWeatherForecastForCities("a,lmn,op")
        Truth.assertThat(viewModel.cityWeatherList.value?.size).isEqualTo(0)
        Truth.assertThat(viewModel.toastMessage.value)
            .isEqualTo(context.getString(R.string.weather_city_error, "a, lmn and op"))
    }

    @Test
    fun fetchWeatherForecastForCities_Loading() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.fetchWeatherForecastForCities("abc,def,ijk,lmn,op")
        Truth.assertThat(viewModel.isLoading.value).isEqualTo(true)
        mainCoroutineRule.resumeDispatcher()
        Truth.assertThat(viewModel.isLoading.value).isEqualTo(false)
        Truth.assertThat(viewModel.cityWeatherList.value?.size).isEqualTo(5)

    }
}