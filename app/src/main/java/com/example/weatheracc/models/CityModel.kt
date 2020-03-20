package com.example.weatheracc.models

data class CityModel(
    val id: String,
    val name: String,
    val date: String = "19.03.2020",
    val status: String,
    val temperature: Int
)