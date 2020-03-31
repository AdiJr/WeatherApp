package com.example.weatheracc.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weatheracc.models.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [WeatherForecast::class], version = 1)
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
                AppDatabase::class.java, "weather_acc.db"
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        populateDB(
                            context
                        )
                    }

                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        super.onDestructiveMigration(db)
                        populateDB(
                            context
                        )
                    }
                })
                .build()
        }

        private fun populateDB(context: Context) {
            val list = listOf(
                WeatherForecast(
                    Coord(-16.92, 145.77),
                    listOf(
                        Weather(802, "Clouds", "scattered clouds", "03n")
                    ),
                    "stations",
                    Main(300.15, 1007, 74, 300.15, 300.15),
                    10000,
                    Wind(3.6, 160),
                    Clouds(40),
                    1485790200,
                    Sys(1, 8166, "AU", 1485720272, 1485766550),
                    2172797,
                    "Cairns",
                    200
                ),
                WeatherForecast(
                    Coord(-0.13, 51.51),
                    listOf(
                        Weather(802, "Clouds", "scattered clouds", "03n")
                    ),
                    "stations",
                    Main(279.24, 1031, 80, 278.15, 280.37),
                    10000,
                    Wind(5.1, 10),
                    Clouds(40),
                    1585600321,
                    Sys(1, 1412, "GB", 1585546767, 1585593001),
                    2643743,
                    "London",
                    200
                )
            )
            GlobalScope.launch {
                buildDatabase(
                    context
                ).weatherForecastDao().insert(list)
            }
        }
    }
}