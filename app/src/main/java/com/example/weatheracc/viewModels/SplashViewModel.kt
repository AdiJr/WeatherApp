package com.example.weatheracc.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatheracc.repository.Repository
import com.example.weatheracc.repository.local.getUnits
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    val repository: Repository,
    val sharedPreferences: SharedPreferences
) : ViewModel() {

    val proceed = MutableLiveData<Boolean>()
    var isEmpty = true

    fun fetchOnline() {
        Timber.d("fetchOnline called!")
        viewModelScope.launch {
            val animationDone = async {
                delay(2_500)
                true
            }
            val fetchDone = async {
                try {
                    val list = repository.getWeatherList().map { it.id }
                    isEmpty = if (list.isNotEmpty()) {
                        repository.fetchWeatherByCityIdList(list, sharedPreferences.getUnits())
                        false
                    } else {
                        true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                true
            }
            animationDone.await() && fetchDone.await()
            proceed.postValue(true)
        }
    }

    fun fetchOffline() {
        Timber.d("fetchOnline called!")
        viewModelScope.launch {
            val animationDone = async {
                delay(2_500)
                true
            }
            val list = repository.getWeatherList().map { it.id }
            isEmpty = list.isEmpty()
            animationDone.await()
            proceed.postValue(true)
        }
    }
}