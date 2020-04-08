package com.example.weatheracc.di.components

import com.example.weatheracc.WeatherApplication
import com.example.weatheracc.di.modules.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        ActivityModule::class,
        FragmentsModule::class,
        ContextModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class,
        RemoteModule::class
    ]
)

interface AppComponent : AndroidInjector<WeatherApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherApplication>()
}