package com.example.weatheracc.repository.remote

import com.example.weatheracc.models.FindCityWeatherResponse
import com.example.weatheracc.models.WeatherCityListResponse
import com.example.weatheracc.models.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("data/2.5/weather?appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun getWeatherByCityId(
        @Query("id") cityId: Long,
        @Query("units") units: String
    ): WeatherForecast

    @GET("data/2.5/group?appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun getWeatherByCityIdList(
        @Query("id") cityListId: String,
        @Query("units") units: String,
        @Query("lang") language: String
    ): WeatherCityListResponse

    @GET("data/2.5/find?appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun findCityWeatherByName(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("lang") language: String
    ): FindCityWeatherResponse
}