package com.example.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.SearchedCityModel
import kotlinx.android.synthetic.main.item_searched_city.view.*

class SearchedCitiesAdapter(
    private val listener: (SearchedCityModel) -> Unit
) : ListAdapter<SearchedCityModel, SearchedCitiesAdapter.CitiesViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CitiesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_searched_city,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchedCityModel>() {
            override fun areItemsTheSame(
                oldItem: SearchedCityModel,
                newItem: SearchedCityModel
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: SearchedCityModel,
                newItem: SearchedCityModel
            ): Boolean =
                oldItem == newItem
        }
    }

    class CitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: SearchedCityModel, listener: (SearchedCityModel) -> Unit) {
            itemView.apply {
                tvSearchedCityName.text = city.name
                setOnClickListener { listener(city) }
            }
        }
    }

}
