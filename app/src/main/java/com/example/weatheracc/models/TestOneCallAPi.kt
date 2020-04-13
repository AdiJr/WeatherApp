package com.example.weatheracc.models

data class TestOneCallAPi(
    val id: String,
    val day: String,
    val temp: Int
)

data class HoursDetails(
    val hour: String,
    val description: String,
    val temp: Int,
    val isLast: Boolean
)

data class ForecastDetails(
    val title: String,
    val value: String
)