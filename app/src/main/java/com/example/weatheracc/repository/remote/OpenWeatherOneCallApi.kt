package com.example.weatheracc.repository.remote

import com.example.weatheracc.models.WeatherOneCallApi
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherOneCallApi {

    @GET("data/2.5/onecall?appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun getWeatherfromCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("lang") language: String
    ): WeatherOneCallApi

}