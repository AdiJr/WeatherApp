package com.example.weatheracc.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatheracc.models.WeatherForecast
import com.example.weatheracc.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchedCitiesViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    val cityList = MutableLiveData<List<WeatherForecast>>()
    val errorMessage = MutableLiveData<String>()

    fun searchCity(cityName: String) {
        viewModelScope.launch {
            try {
                val result = repository.findCityByName(cityName)
                cityList.postValue(result.list)
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.toString())
            }
        }
    }

    fun storeCity(weatherForecast: WeatherForecast) {
        viewModelScope.launch {
            repository.storeCity(weatherForecast)
            val toStore = repository.fetchWeatherByCoordinates(
                weatherForecast.coordinates.lat,
                weatherForecast.coordinates.lon
            )
            repository.storeCity(toStore)
        }
    }
}