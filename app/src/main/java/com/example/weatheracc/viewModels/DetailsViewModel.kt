package com.example.weatheracc.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatheracc.models.WeatherOneCallApi
import com.example.weatheracc.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val city = MutableLiveData<WeatherOneCallApi>()

    fun storeCityByCoordinates(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val result = repository.fetchWeatherByCoordinates(latitude, longitude)
                city.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
