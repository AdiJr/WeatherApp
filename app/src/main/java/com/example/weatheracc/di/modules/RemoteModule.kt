package com.example.weatheracc.di.modules

import com.example.weatheracc.BuildConfig
import com.example.weatheracc.repository.remote.OpenWeatherApi
import com.example.weatheracc.repository.remote.OpenWeatherOneCallApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideOpenWeatherService(okHttpClient: OkHttpClient): OpenWeatherApi =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(OpenWeatherApi::class.java)

    @Provides
    @Singleton
    fun provideOpenWeatherOneCallService(okHttpClient: OkHttpClient): OpenWeatherOneCallApi =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(OpenWeatherOneCallApi::class.java)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
}