package com.example.weatheracc.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.weatheracc.repository.Repository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel()
