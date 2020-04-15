package com.example.weatheracc.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "forecast_one_call_api")
data class WeatherOneCallApi(
    @PrimaryKey
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("timezone") val timezone: String,
    @Embedded
    @SerializedName("current") val current: Current,
    @SerializedName("hourly") val hourly: List<Hourly>,
    @SerializedName("daily") val daily: List<Daily>
)

data class Current(
    @SerializedName("dt") val dt: Int,
    @SerializedName("sunrise") val sunrise: Int,
    @SerializedName("sunset") val sunset: Int,
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feels_like: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("dew_point") val dew_point: Double,
    @SerializedName("uvi") val uvi: Double,
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("wind_speed") val wind_speed: Double,
    @SerializedName("wind_deg") val wind_deg: Int,
    @SerializedName("wind_gust") val wind_gust: Int,
    /*@SerializedName("rain") val rain: Int,*/
    @SerializedName("snow") val snow: Int,
    @SerializedName("weather") val weather: List<WeatherOneCall>
)

data class Hourly(
    @SerializedName("dt") val dt: Int,
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feels_like: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("dew_point") val dew_point: Double,
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("wind_gust") val wind_gust: Int,
    /*  @SerializedName("rain") val rain: Int,*/
    @SerializedName("snow") val snow: Int,
    @SerializedName("wind_speed") val wind_speed: Double,
    @SerializedName("wind_deg") val wind_deg: Int,
    @SerializedName("weather") val weather: List<WeatherOneCall>
)

data class Daily(
    @SerializedName("dt") val dt: Int,
    @SerializedName("sunrise") val sunrise: Int,
    @SerializedName("sunset") val sunset: Int,
    @SerializedName("temp") val temp: Temp,
    @SerializedName("feels_like") val feels_like: FeelsLike,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("dew_point") val dew_point: Double,
    @SerializedName("wind_speed") val wind_speed: Double,
    @SerializedName("wind_deg") val wind_deg: Int,
    @SerializedName("weather") val weather: List<WeatherOneCall>,
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("uvi") val uvi: Double
)

data class FeelsLike(
    @SerializedName("day") val day: Double,
    @SerializedName("night") val night: Double,
    @SerializedName("eve") val eve: Double,
    @SerializedName("morn") val morn: Double
)

data class WeatherOneCall(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class Temp(
    @SerializedName("day") val day: Double,
    @SerializedName("min") val min: Double,
    @SerializedName("max") val max: Double,
    @SerializedName("night") val night: Double,
    @SerializedName("eve") val eve: Double,
    @SerializedName("morn") val morn: Double
)

data class ForecastDetails(
    val title: String,
    val value: String
)