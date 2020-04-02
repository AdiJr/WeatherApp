package com.example.weatheracc.modules

import androidx.lifecycle.ViewModel
import com.example.weatheracc.viewModels.HomeViewModel
import com.example.weatheracc.viewModels.SavedCitiesViewModel
import com.example.weatheracc.viewModels.SearchedCitiesViewModel
import com.example.weatheracc.viewModels.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(key = HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = SavedCitiesViewModel::class)
    abstract fun bindSavedCitiesViewModel(viewModel: SavedCitiesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = SearchedCitiesViewModel::class)
    abstract fun bindSearchedCitiesViewModel(viewModel: SearchedCitiesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel
}