package com.example.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.weatheracc.R
import com.example.weatheracc.viewModels.ForecastCityViewModel

class ForecastCityFragment : Fragment() {

    companion object {
        fun newInstance() = ForecastCityFragment()
    }

    private lateinit var viewModel: ForecastCityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_city_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForecastCityViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
