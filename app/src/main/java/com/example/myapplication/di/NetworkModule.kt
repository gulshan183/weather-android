package com.example.myapplication.di

import com.example.myapplication.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */
@Module
class NetworkModule {

    /**
     * Provides retrofit object. Annotated Singleton, therefore will have single instance
     * for complete lifecycle of application.
     *
     * @see [Retrofit]
     *
     * @return [Retrofit]
     */
    @Singleton
    @Provides
    fun provideRetrofitService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHTTPClient())
            .build()
    }

    /**
     * Provides OkHttpClient having interceptor for headers and logs
     *
     * @return [OkHttpClient]
     */
    private fun getOkHTTPClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("appid", "aa4c44684e18c81cf809b7e07c7bd989")
                    .addQueryParameter("units", "metric")
                    .build()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
            .addInterceptor(getHTTPLoggingInterceptor())
            .build()
    }

    /**
     * @return [HttpLoggingInterceptor] for logging Network calls
     */
    private fun getHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    }
}