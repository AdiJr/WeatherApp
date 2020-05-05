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
                oldItem.coordinates.lat == newItem.coordinates.lat

            override fun areContentsTheSame(
                oldItem: WeatherForecast,
                newItem: WeatherForecast
            ): Boolean =
                oldItem == newItem
        }
    }

    class CitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            city: WeatherForecast,
            listener: (WeatherForecast) -> Unit
        ) {
            itemView.apply {
                city.weather.firstOrNull()?.let {
                    if (System.currentTimeMillis() > city.sys.sunrise) {
                        when (it.main) {
                            "Clouds" -> {
                                setBackgroundResource(R.drawable.background_cloudy)
                                ivWeatherIcon.setImageResource(R.drawable.icon_clouds)
                            }
                            "Snow" -> {
                                setBackgroundResource(R.drawable.background_cloudy)
                                ivWeatherIcon.setImageResource(R.drawable.icon_snow)
                            }
                            "Rain" -> {
                                setBackgroundResource(R.drawable.background_cloudy)
                                ivWeatherIcon.setImageResource(R.drawable.icon_rain)
                            }
                            "Thunderstorm" -> {
                                setBackgroundResource(R.drawable.background_mist)
                                ivWeatherIcon.setImageResource(R.drawable.icon_thunder)
                            }
                            "Mist" -> {
                                setBackgroundResource(R.drawable.background_mist)
                                ivWeatherIcon.setImageResource(R.drawable.icon_mist)
                            }
                        }
                    } else {
                        if (System.currentTimeMillis() > city.sys.sunset) {
                            setBackgroundResource(R.drawable.background_night)
                            ivWeatherIcon.visibility = View.GONE
                        }
                    }
                }

                tvCityName.text = city.name
                tvCurrentTemp.text = "${city.main.temp.roundToInt()}\u00B0"
                tvTemperatureMinMax.text =
                    "${city.main.temp_max.roundToInt()}\u00B0 / ${city.main.temp_min.roundToInt()}\u00B0"

                setOnClickListener { listener(city) }
            }
        }
    }
}