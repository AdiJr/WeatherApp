package com.example.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.ForecastDetails
import kotlinx.android.synthetic.main.item_current_conditions.view.*

class CurrentConditionsAdapter :
    ListAdapter<ForecastDetails, CurrentConditionsAdapter.ConditionsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ConditionsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_current_conditions,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ConditionsViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ForecastDetails>() {
            override fun areItemsTheSame(
                oldItem: ForecastDetails,
                newItem: ForecastDetails
            ): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(
                oldItem: ForecastDetails,
                newItem: ForecastDetails
            ): Boolean =
                oldItem == newItem
        }
    }

    class ConditionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(details: ForecastDetails) {
            itemView.apply {
                tvConditionTitle.text = details.title
                tvConditionDetails.text = details.value.toString()
            }
        }
    }
}