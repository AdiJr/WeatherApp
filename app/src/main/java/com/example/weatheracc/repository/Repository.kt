package com.example.weatheracc.repository

import com.example.weatheracc.models.Units
import com.example.weatheracc.models.WeatherForecast
import com.example.weatheracc.repository.local.WeatherForecastDao
import com.example.weatheracc.repository.remote.OpenWeatherApi
import java.util.*

class Repository(
    private val openWeatherApi: OpenWeatherApi,
    private val weatherForecastDao: WeatherForecastDao
) {

    fun getWeatherListFlow() = weatherForecastDao.getAllFlow()

    suspend fun getWeatherList() = weatherForecastDao.getAll()

    suspend fun fetchWeatherByCityIdList(cityIdList: List<Long>, units: Units = Units.METRIC) =
        openWeatherApi.getWeatherByCityIdList(
            cityIdList.fold("", { acc: String, cityId: Long -> "$acc$cityId," }),
            units.name.toLowerCase(Locale.ROOT)
        ).also { weatherForecastDao.insert(it.list) }

    suspend fun findCityByName(cityName: String, units: Units = Units.METRIC) =
        openWeatherApi.findCityWeatherByName(cityName, units.name.toLowerCase(Locale.ROOT))

    suspend fun storeCity(weatherForecast: WeatherForecast) =
        weatherForecastDao.insert(weatherForecast)
}