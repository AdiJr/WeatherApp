package com.example.weatheracc.modules

import com.example.weatheracc.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityModule {

    @ContributesAndroidInjector
    //internal - visibility only to current module (app), abstract function doesn't return any instance, has no implementation
    internal abstract fun mainActivity(): MainActivity
}