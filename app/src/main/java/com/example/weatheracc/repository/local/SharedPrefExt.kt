package com.example.weatheracc.repository.local

import android.content.SharedPreferences
import com.example.weatheracc.models.Units

fun SharedPreferences.getUnits(): Units =
    Units.valueOf(
        getString(Units::class.java.simpleName, Units.METRIC.name)
            ?: Units.METRIC.name
    )

fun SharedPreferences.setUnits(units: Units) = edit()
    .putString(Units::class.java.simpleName, units.name)
    .apply()