package com.example.weatheracc.di.modules

import com.example.weatheracc.ui.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentsModule {

    @ContributesAndroidInjector
    internal abstract fun bindSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    internal abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun bindSavedCitiesFragment(): SavedCitiesFragment

    @ContributesAndroidInjector
    internal abstract fun bindSearchedCitiesFragment(): SearchedCitiesFragment

    @ContributesAndroidInjector
    internal abstract fun bindDetailsFragment(): DetailsFragment
}