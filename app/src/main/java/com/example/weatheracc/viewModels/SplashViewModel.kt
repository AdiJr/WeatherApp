package com.example.weatheracc.viewModels

import androidx.lifecycle.ViewModel
import com.example.weatheracc.repository.Repository
import javax.inject.Inject

class SplashViewModel @Inject constructor(repository: Repository) : ViewModel()