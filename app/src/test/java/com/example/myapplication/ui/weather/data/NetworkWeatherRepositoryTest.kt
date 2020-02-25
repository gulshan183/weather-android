package com.example.myapplication.ui.weather.data

import com.example.myapplication.`interface`.APIServiceMock
import com.example.myapplication.constant.APIResponseState
import com.example.myapplication.network.WeatherNetworkService
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

/**
 * Created by Gulshan Ahluwalia on 2020-02-06.
 */
@RunWith(MockitoJUnitRunner::class)
class NetworkWeatherRepositoryTest {
    @Mock
    private lateinit var mockRetrofit: Retrofit
    private lateinit var repository: NetworkWeatherRepository
    private lateinit var apiServiceMock: APIServiceMock


    /**
     * Mocks the behaviour of the Retrofit. Uses [MockRetrofit] to achieve the same.
     * A [BehaviorDelegate] is used to mock the API Service used by Retrofit.
     *
     */
    @ExperimentalCoroutinesApi
    @Before
    fun createRepository() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://example.com").build()
        val mockRetrofit2 = MockRetrofit.Builder(retrofit)
            .networkBehavior(NetworkBehavior.create()).build()

        val delegate: BehaviorDelegate<WeatherNetworkService?> =
            mockRetrofit2.create(WeatherNetworkService::class.java)

        apiServiceMock = APIServiceMock(delegate)
        Mockito.`when`(mockRetrofit.create(WeatherNetworkService::class.java))
            .thenReturn(apiServiceMock)
        repository = NetworkWeatherRepository(this.mockRetrofit)
    }


    @Test
    fun fetchCurrentWeather_success() = runBlocking {
        val data = repository.fetchCurrentWeather("London")
        Truth.assertThat(data?.dt).isNotNull()
    }

    @Test
    fun fetchCurrentWeather_errorNotFound() = runBlocking {
        apiServiceMock.apiResponseState = APIResponseState.NOT_FOUND
        try {
            repository.fetchCurrentWeather("abc")
            Assert.fail("Should have thrown exception")
        } catch (e: NetworkWeatherRepository.RepositoryException) {
            Truth.assertThat(e.message).isEqualTo("Resource not found")
        }
    }

    @Test
    fun fetchCurrentWeather_errorAPIKey() = runBlocking {
        apiServiceMock.apiResponseState = APIResponseState.API_KEY_ERROR
        try {
            repository.fetchCurrentWeather("London")
            Assert.fail("Should have thrown exception")
        } catch (e: NetworkWeatherRepository.RepositoryException) {
            Truth.assertThat(e.message).isEqualTo("Invalid API key")
        }
    }

    @Test
    fun fetchCurrentWeather_errorNetwork() = runBlocking {
        apiServiceMock.apiResponseState = APIResponseState.NETWORK_ERROR
        try {
            repository.fetchCurrentWeather("London")
            Assert.fail("Should have thrown exception")
        } catch (e: NetworkWeatherRepository.RepositoryException) {
            Truth.assertThat(e.message)
                .isEqualTo("Unable to connect to our servers, check your Internet Connection.")
        }
    }


    @Test
    fun fetchWeatherForecast_success() = runBlocking {
        val data = repository.fetchWeatherForecast(31.3, 31.21)
        Truth.assertThat(data?.city?.name).isNotNull()
    }

    @Test
    fun fetchWeatherForecast_errorNotFound() = runBlocking {
        apiServiceMock.apiResponseState = APIResponseState.NOT_FOUND
        try {
            repository.fetchWeatherForecast(31.3, 31.21)
            Assert.fail("Should have thrown exception")
        } catch (e: NetworkWeatherRepository.RepositoryException) {
            Truth.assertThat(e.message).isEqualTo("Resource not found")
        }
    }

    @Test
    fun fetchWeatherForecast_errorAPIKey() = runBlocking {
        apiServiceMock.apiResponseState = APIResponseState.API_KEY_ERROR
        try {
            repository.fetchWeatherForecast(31.3, 31.21)
            Assert.fail("Should have thrown exception")
        } catch (e: NetworkWeatherRepository.RepositoryException) {
            Truth.assertThat(e.message).isEqualTo("Invalid API key")
        }
    }

    @Test
    fun fetchWeatherForecast_errorNetwork() = runBlocking {
        apiServiceMock.apiResponseState = APIResponseState.NETWORK_ERROR
        try {
            repository.fetchWeatherForecast(31.3, 31.21)
            Assert.fail("Should have thrown exception")
        } catch (e: NetworkWeatherRepository.RepositoryException) {
            Truth.assertThat(e.message)
                .isEqualTo("Unable to connect to our servers, check your Internet Connection.")
        }
    }


}
