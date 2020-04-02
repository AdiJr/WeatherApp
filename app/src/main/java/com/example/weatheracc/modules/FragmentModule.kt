package com.example.weatheracc.modules

import com.example.weatheracc.ui.fragment.HomeFragment
import com.example.weatheracc.ui.fragment.SavedCitiesFragment
import com.example.weatheracc.ui.fragment.SearchedCitiesFragment
import com.example.weatheracc.ui.fragment.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun bindSavedCitiesFragment(): SavedCitiesFragment

    @ContributesAndroidInjector
    internal abstract fun bindSearchedCitiesFragment(): SearchedCitiesFragment

    @ContributesAndroidInjector
    internal abstract fun bindSplashFragment(): SplashFragment
}