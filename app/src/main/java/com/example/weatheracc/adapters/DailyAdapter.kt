package com.example.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.Daily
import kotlinx.android.synthetic.main.item_daily_forecast.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DailyAdapter : ListAdapter<Daily, DailyAdapter.DetailsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DetailsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_daily_forecast,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Daily>() {
            override fun areItemsTheSame(
                oldItem: Daily,
                newItem: Daily
            ): Boolean =
                oldItem.dt == newItem.dt

            override fun areContentsTheSame(
                oldItem: Daily,
                newItem: Daily
            ): Boolean =
                oldItem == newItem
        }
    }

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(day: Daily) {
            itemView.apply {
                val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
                val date = day.dt.toLong() * 1000

                tvDayName.text = sdf.format(date)
                tvDayTemp.text = day.temp.max.roundToInt()
                    .toString() + "\u00B0" + "/ " + day.temp.min.roundToInt().toString() + "\u00B0"

                day.weather.firstOrNull()?.let {
                    when (it.main) {
                        "Clear" -> {
                            ivDayIcon.setImageResource(R.drawable.icon_sun)
                        }
                        "Clouds" -> {
                            ivDayIcon.setImageResource(R.drawable.icon_clouds)
                        }
                        "Snow" -> ivDayIcon.setImageResource(R.drawable.icon_snow)
                        "Rain" -> ivDayIcon.setImageResource(R.drawable.icon_rain)
                        "Thunderstorm" -> ivDayIcon.setImageResource(R.drawable.icon_thunder)
                        "Mist" -> ivDayIcon.setImageResource(R.drawable.icon_mist)
                    }
                }
            }
        }
    }
}