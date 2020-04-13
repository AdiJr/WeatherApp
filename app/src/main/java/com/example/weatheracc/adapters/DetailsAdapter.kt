package com.example.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.models.TestOneCallAPi
import kotlinx.android.synthetic.main.item_daily_forecast.view.*

class DetailsAdapter(
    private val listener: (TestOneCallAPi) -> Unit
) : ListAdapter<TestOneCallAPi, DetailsAdapter.DetailsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DetailsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_daily_forecast,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TestOneCallAPi>() {
            override fun areItemsTheSame(
                oldItem: TestOneCallAPi,
                newItem: TestOneCallAPi
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TestOneCallAPi,
                newItem: TestOneCallAPi
            ): Boolean =
                oldItem == newItem
        }
    }

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: TestOneCallAPi, listener: (TestOneCallAPi) -> Unit) {
            itemView.apply {
                tvDayName.text = city.day
                tvDayTemp.text = "${city.temp}\u00B0"
                ivDayIcon.setBackgroundResource(R.drawable.icon_sun)
                setOnClickListener { listener(city) }
            }
        }
    }
}