package com.example.weatheracc.repository.local

import androidx.room.*
import com.example.weatheracc.models.WeatherForecast
import com.example.weatheracc.models.WeatherOneCallApi
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherForecastDao: WeatherForecast): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherForecastDao: List<WeatherForecast>): List<Long>

    @Update
    suspend fun update(weatherForecastDao: WeatherForecast): Int

    @Delete
    suspend fun delete(weatherForecastDao: WeatherForecast): Int

    @Query("SELECT * FROM current_weather")
    fun getAllFlow(): Flow<List<WeatherForecast>>

    @Query("SELECT * FROM current_weather")
    suspend fun getAll(): List<WeatherForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherForecastDao: WeatherOneCallApi): Long

    @Update
    suspend fun update(weatherForecastDao: WeatherOneCallApi): Int

    @Delete
    suspend fun delete(weatherForecastDao: WeatherOneCallApi): Int

    @Query("SELECT * FROM forecast_one_call_api WHERE lat LIKE :latitude AND lon LIKE :longitude")
    suspend fun getForecastForCity(latitude: Double, longitude: Double): WeatherOneCallApi
}
