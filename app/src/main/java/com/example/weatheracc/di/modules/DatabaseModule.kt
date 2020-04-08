package com.example.weatheracc.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.example.weatheracc.repository.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideSharedPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences("weather_app", Context.MODE_PRIVATE)
}