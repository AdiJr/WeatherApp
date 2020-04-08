package com.example.weatheracc.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatheracc.models.Units
import com.example.weatheracc.repository.Repository
import com.example.weatheracc.repository.local.getUnits
import com.example.weatheracc.repository.local.setUnits
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val units = MutableLiveData<Units>()

    fun updateUnits() {
        val currentUnits = sharedPreferences.getUnits()
        val newUnits = if (currentUnits == Units.METRIC) Units.IMPERIAL else Units.METRIC

        viewModelScope.launch {
            repository.getWeatherList().map { it.id }.let {
                try {
                    repository.fetchWeatherByCityIdList(it, newUnits)
                    sharedPreferences.setUnits(newUnits)
                    units.postValue(newUnits)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
