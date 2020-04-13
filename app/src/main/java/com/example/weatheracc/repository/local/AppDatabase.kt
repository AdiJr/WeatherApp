package com.example.weatheracc.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatheracc.models.WeatherForecast
import com.example.weatheracc.models.WeatherOneCallApi

@Database(entities = [WeatherForecast::class, WeatherOneCallApi::class], version = 1)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherForecastDao(): WeatherForecastDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance
                ?: synchronized(this) {
                    instance
                        ?: buildDatabase(
                            context
                        ).also { instance = it }
                }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "my_weather.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}