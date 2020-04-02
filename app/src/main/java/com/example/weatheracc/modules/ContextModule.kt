package com.example.weatheracc.modules

import android.content.Context
import com.example.weatheracc.WeatherApplication
import dagger.Module
import dagger.Provides

@Module
class ContextModule {

    @Provides
    fun providesContext(application: WeatherApplication): Context = application.applicationContext
}