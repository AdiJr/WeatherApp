package com.example.weatheracc.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity(tableName = "weather_forecast")
@Parcelize
data class WeatherForecast(
    @Embedded
    @SerializedName("coord") val coordinates: @RawValue Coord,
    @SerializedName("weather") val weather: @RawValue List<Weather>,
    @SerializedName("base") val base: String?,
    @Embedded
    @SerializedName("main") val main: @RawValue Main,
    @SerializedName("visibility") val visibility: Int,
    @Embedded
    @SerializedName("wind") val wind: @RawValue Wind,
    @Embedded
    @SerializedName("clouds") val clouds: @RawValue Clouds,
    @SerializedName("dt") val dt: Int,
    @Embedded
    @SerializedName("sys") val sys: @RawValue Sys,
    @PrimaryKey
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("cod") val cod: Int
) : Parcelable

data class Wind(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val deg: Int
)

data class Weather(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class Sys(
    @SerializedName("type") val type: Int,
    @SerializedName("id")
    @ColumnInfo(name = "sys_id") val id: Int,
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: Int,
    @SerializedName("sunset") val sunset: Int
)

data class Main(
    @SerializedName("temp") val temp: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("temp_min") val temp_min: Double,
    @SerializedName("temp_max") val temp_max: Double
)

data class Coord(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)

data class Clouds(
    @SerializedName("all") val all: Int
)

data class WeatherCityListResponse(
    @SerializedName("cnt") val count: Int,
    @SerializedName("list") val list: List<WeatherForecast>
)

data class FindCityWeatherResponse(
    @SerializedName("message") val message: String,
    @SerializedName("cod") val cod: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("list") val list: List<WeatherForecast>
)

enum class Units {
    METRIC,
    IMPERIAL
}