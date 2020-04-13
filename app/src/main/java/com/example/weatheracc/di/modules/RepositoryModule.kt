package com.example.weatheracc.di.modules

import com.example.weatheracc.repository.Repository
import com.example.weatheracc.repository.local.AppDatabase
import com.example.weatheracc.repository.remote.OpenWeatherApi
import com.example.weatheracc.repository.remote.OpenWeatherOneCallApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        openWeatherApi: OpenWeatherApi,
        openWeatherOneCallApi: OpenWeatherOneCallApi,
        database: AppDatabase
    ) = Repository(openWeatherApi, openWeatherOneCallApi, database.weatherForecastDao())
}