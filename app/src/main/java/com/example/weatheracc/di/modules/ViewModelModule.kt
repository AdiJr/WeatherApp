package com.example.weatheracc.di.modules

import androidx.lifecycle.ViewModel
import com.example.weatheracc.viewModels.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(key = SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

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
    @ViewModelKey(key = DetailsViewModel::class)
    abstract fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel
}