package com.example.weatheracc.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.WeatherForecast
import kotlinx.android.synthetic.main.item_searched_city.view.*

class SearchedCitiesAdapter(
    private val listener: (WeatherForecast) -> Unit
) : ListAdapter<WeatherForecast, SearchedCitiesAdapter.CitySearchViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CitySearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_searched_city,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CitySearchViewHolder, position: Int) =
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

    class CitySearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: WeatherForecast, listener: (WeatherForecast) -> Unit) {
            itemView.apply {
                val searchText = "<b>${city.name}</b>, ${city.sys.country}"
                tvSearchedCityName.text = Html.fromHtml(searchText)
                setOnClickListener { listener(city) }
            }
        }
    }

}
