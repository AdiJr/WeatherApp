package com.example.weatheracc.modules

import com.example.weatheracc.repository.Repository
import com.example.weatheracc.repository.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(database: AppDatabase) = Repository(database.weatherForecastDao())
}