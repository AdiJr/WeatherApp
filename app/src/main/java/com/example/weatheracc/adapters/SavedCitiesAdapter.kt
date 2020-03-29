package com.example.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.CityModel
import kotlinx.android.synthetic.main.item_saved_city.view.*

class SavedCitiesAdapter(
    private val listener: (CityModel) -> Unit
) : ListAdapter<CityModel, SavedCitiesAdapter.CitiesViewHolder>(DIFF_CALLBACK) {

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
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CityModel>() {
            override fun areItemsTheSame(oldItem: CityModel, newItem: CityModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CityModel, newItem: CityModel): Boolean =
                oldItem == newItem
        }
    }

    class CitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: CityModel, listener: (CityModel) -> Unit) {
            itemView.apply {
                when (city.status) {
                    "Sunny" -> setBackgroundResource(R.drawable.hot_background)
                    "Clouds" -> setBackgroundResource(R.drawable.cloudy_background)
                    "Clear Sky" -> setBackgroundResource(R.drawable.clear_background)
                }
                tvCityName.text = city.name
                tvDate.text = city.date
                tvTemperature.text = "${city.temperature}"
                setOnClickListener { listener(city) }
            }
        }
    }
}