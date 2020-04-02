package com.example.weatheracc.components

import com.example.weatheracc.WeatherApplication
import com.example.weatheracc.modules.*
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
        FragmentModule::class,
        ContextModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ]
)

interface AppComponent : AndroidInjector<WeatherApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherApplication>()
}