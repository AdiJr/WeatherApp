package com.example.weatheracc.repository.local

import androidx.room.TypeConverter
import com.example.weatheracc.models.Daily
import com.example.weatheracc.models.Hourly
import com.example.weatheracc.models.Weather
import com.example.weatheracc.models.WeatherOneCall
import com.google.gson.Gson

class RoomTypeConverters {

    @TypeConverter
    fun weatherToJson(value: List<Weather>?): String? = value?.let { Gson().toJson(value) }

    @TypeConverter
    fun jsonToWeather(value: String?): List<Weather>? =
        value?.let {
            Gson().fromJson(
                value,
                Array<Weather>::class.java
            ) as Array<Weather>
        }?.toList()

    @TypeConverter
    fun hourlyToJson(value: List<Hourly>?): String? = value?.let { Gson().toJson(value) }

    @TypeConverter
    fun jsonToHourly(value: String?): List<Hourly>? =
        value?.let {
            Gson().fromJson(
                value,
                Array<Hourly>::class.java
            ) as Array<Hourly>
        }?.toList()

    @TypeConverter
    fun dailyToJson(value: List<Daily>?): String? = value?.let { Gson().toJson(value) }

    @TypeConverter
    fun jsonToDaily(value: String?): List<Daily>? =
        value?.let {
            Gson().fromJson(
                value,
                Array<Daily>::class.java
            ) as Array<Daily>
        }?.toList()

    @TypeConverter
    fun weatherOneCallToJson(value: List<WeatherOneCall>?): String? =
        value?.let { Gson().toJson(value) }

    @TypeConverter
    fun jsonToWeatherOneCall(value: String?): List<WeatherOneCall>? =
        value?.let {
            Gson().fromJson(
                value,
                Array<WeatherOneCall>::class.java
            ) as Array<WeatherOneCall>
        }?.toList()
}