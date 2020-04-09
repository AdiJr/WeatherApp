package com.example.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.WeatherForecast
import kotlinx.android.synthetic.main.item_saved_city.view.*
import java.text.DateFormat.getDateInstance
import java.util.*
import kotlin.math.roundToInt

class SavedCitiesAdapter(
    private val listener: (WeatherForecast) -> Unit
) : ListAdapter<WeatherForecast, SavedCitiesAdapter.CitiesViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CitiesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_saved_city,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherForecast>() {
            override fun areItemsTheSame(
                oldItem: WeatherForecast,
                newItem: WeatherForecast
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: WeatherForecast,
                newItem: WeatherForecast
            ): Boolean =
                oldItem == newItem
        }
    }

    class CitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: WeatherForecast, listener: (WeatherForecast) -> Unit) {
            itemView.apply {
                when (city.weather.firstOrNull()!!.description) {
                    "clear sky" -> {
                        setBackgroundResource(R.drawable.background_clear)
                        weatherIcon.setImageResource(R.drawable.icon_sun)
                    }

                    "few clouds" -> {
                        setBackgroundResource(R.drawable.background_cloudy)
                        weatherIcon.setImageResource(R.drawable.icon_clouds)
                    }
                    "scattered clouds" -> {
                        setBackgroundResource(R.drawable.background_cloudy)
                        weatherIcon.setImageResource(R.drawable.icon_clouds)
                    }
                    "snow" -> weatherIcon.setImageResource(R.drawable.icon_snow)
                    "rain" -> weatherIcon.setImageResource(R.drawable.icon_rain)
                    "thunderstorm" -> weatherIcon.setImageResource(R.drawable.icon_thunder)
                    "mist" -> weatherIcon.setImageResource(R.drawable.icon_mist)
                    "broken clouds" -> setBackgroundResource(R.drawable.background_cloudy)
                    "overcast clouds" -> setBackgroundResource(R.drawable.background_cloudy)
                    else -> {
                        setBackgroundResource(R.drawable.background_clear)
                        weatherIcon.setImageResource(R.drawable.icon_sun)
                    }
                }
                if (city.main.temp > 25) setBackgroundResource(R.drawable.hot_background)

                val updateTimeMillis = city.dt.toLong() * 1000
                val simpleDateFormat = getDateInstance()
                val updateDate = Date(updateTimeMillis)

                tvCityName.text = city.name
                tvDate.text = simpleDateFormat.format(updateDate)
                tvTemperature.text =
                    "${city.main.temp_max.roundToInt()}\u00B0 / ${city.main.temp_min.roundToInt()}\u00B0"
                setOnClickListener { listener(city) }
            }
        }
    }
}