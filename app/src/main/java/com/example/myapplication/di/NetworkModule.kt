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

    @Singleton
    @Provides
    fun provideRetrofitService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHTTPClient())
            .build()
    }

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

    private fun getHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    }
}