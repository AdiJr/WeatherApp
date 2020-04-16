package com.example.weatheracc.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatheracc.models.Units
import com.example.weatheracc.models.WeatherForecast
import com.example.weatheracc.repository.Repository
import com.example.weatheracc.repository.local.getUnits
import com.example.weatheracc.repository.local.setUnits
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SavedCitiesViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val weatherList = MutableLiveData<List<WeatherForecast>>()
    val units = MutableLiveData<Units>()
    val currentUnits = sharedPreferences.getUnits()

    init {
        repository.getWeatherListFlow()
            .onStart {
                Timber.d("Flow starting")
            }
            .onCompletion {
                Timber.d("Flow complete")
            }
            .catch {
                Timber.d("Flow error $it")
            }
            .onEach {
                Timber.d("Flow success $it")
                weatherList.value = it
            }
            .launchIn(viewModelScope)
        units.value = sharedPreferences.getUnits()
    }

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

    fun refreshData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val toStore = repository.fetchWeatherByCoordinates(latitude, longitude)
            repository.storeCity(toStore)
        }
    }

    fun getCityOffline(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                repository.getForecast(latitude, longitude)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}