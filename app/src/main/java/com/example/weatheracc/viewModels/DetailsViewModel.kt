package com.example.weatheracc.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatheracc.models.Daily
import com.example.weatheracc.models.Hourly
import com.example.weatheracc.models.WeatherOneCallApi
import com.example.weatheracc.repository.Repository
import com.example.weatheracc.repository.local.getUnits
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: Repository,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val dailyList = MutableLiveData<List<Daily>>()
    val hourlyList = MutableLiveData<List<Hourly>>()
    val allList = MutableLiveData<WeatherOneCallApi>()

    val currentUnits = sharedPreferences.getUnits()

    fun getCity(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val result = repository.getForecast(latitude, longitude)
                dailyList.postValue(result.daily)
                hourlyList.postValue(result.hourly)
                allList.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getCityOnline(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val result = repository.fetchWeatherByCoordinates(latitude, longitude)
                dailyList.postValue(result.daily)
                hourlyList.postValue(result.hourly)
                allList.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun refreshData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val toStore = repository.fetchWeatherByCoordinates(latitude, longitude)
            repository.storeCity(toStore)
            repository.getForecast(latitude, longitude)
        }
    }
}
