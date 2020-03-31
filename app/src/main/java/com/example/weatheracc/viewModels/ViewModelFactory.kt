package com.example.weatheracc.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatheracc.repository.Repository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            SavedCitiesViewModel::class.java -> {
                SavedCitiesViewModel(repository) as T
            }
            SplashViewModel::class.java -> {
                SplashViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("unknown model class $modelClass")
            }
        }
    }
}