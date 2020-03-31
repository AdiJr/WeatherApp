package com.example.weatheracc.repository

import com.example.weatheracc.repository.local.WeatherForecastDao

class Repository(private val weatherForecastDao: WeatherForecastDao) {
    fun getWeatherList() = weatherForecastDao.getAll()
}