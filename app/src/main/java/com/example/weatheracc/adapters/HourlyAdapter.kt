package com.example.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.HoursDetails
import kotlinx.android.synthetic.main.item_forecast_hourly.view.*

class HourlyAdapter : ListAdapter<HoursDetails, HourlyAdapter.HourlyViewHolder>(DIFF_CALLBACK) {

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
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HoursDetails>() {
            override fun areItemsTheSame(
                oldItem: HoursDetails,
                newItem: HoursDetails
            ): Boolean =
                oldItem.hour == newItem.hour

            override fun areContentsTheSame(
                oldItem: HoursDetails,
                newItem: HoursDetails
            ): Boolean =
                oldItem == newItem
        }
    }

    class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(hourly: HoursDetails) {
            itemView.apply {
                tvHour.text = hourly.hour
                tvHourlyDescription.text = hourly.description
                tvHourlyTemp.text = "${hourly.temp}\u00B0"
                if (hourly.isLast) hourlyDivider.visibility = View.GONE
            }
        }
    }
}