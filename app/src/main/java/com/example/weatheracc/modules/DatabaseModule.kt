package com.example.weatheracc.modules

import android.content.Context
import com.example.weatheracc.repository.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = AppDatabase.getInstance(context)
}