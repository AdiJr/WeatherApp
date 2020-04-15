package com.example.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.Hourly
import kotlinx.android.synthetic.main.item_forecast_hourly.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class HourlyAdapter : ListAdapter<Hourly, HourlyAdapter.HourlyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HourlyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_forecast_hourly,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Hourly>() {
            override fun areItemsTheSame(
                oldItem: Hourly,
                newItem: Hourly
            ): Boolean =
                oldItem.dt == newItem.dt

            override fun areContentsTheSame(
                oldItem: Hourly,
                newItem: Hourly
            ): Boolean =
                oldItem == newItem
        }
    }

    class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(hour: Hourly) {
            itemView.apply {
                val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
                val date = hour.dt.toLong() * 1000

                tvHour.text = sdf.format(date)
                tvHourlyDescription.text = hour.weather.firstOrNull()!!.description.substring(0, 1)
                    .toUpperCase() + hour.weather.firstOrNull()!!.description.substring(1)
                    .toLowerCase()
                tvHourlyTemp.text = hour.temp.roundToInt().toString() + "\u00B0"

                when (hour.weather.firstOrNull()!!.main) {
                    "Clear" -> {
                        ivHourlyIcon.setImageResource(R.drawable.icon_sun)
                    }
                    "Clouds" -> {
                        ivHourlyIcon.setImageResource(R.drawable.icon_clouds)
                    }
                    "Snow" -> ivHourlyIcon.setImageResource(R.drawable.icon_snow)
                    "Rain" -> ivHourlyIcon.setImageResource(R.drawable.icon_rain)
                    "Thunderstorm" -> ivHourlyIcon.setImageResource(R.drawable.icon_thunder)
                    "Mist" -> ivHourlyIcon.setImageResource(R.drawable.icon_mist)
                }
            }
        }
    }
}