package com.example.weatheracc.viewModels

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
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
    val recents = MutableLiveData<List<WeatherForecast>>()

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

    fun getRecents() {
        viewModelScope.launch {
            recents.postValue(repository.getWeatherList())
        }
    }

    //TODO: fix this method, cause keyboard still shows
    fun hideKeyboard(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = View(context)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}