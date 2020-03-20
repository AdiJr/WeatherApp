package com.example.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.weatheracc.R
import com.example.weatheracc.adapters.CitiesAdapter
import com.example.weatheracc.models.CityModel
import com.example.weatheracc.viewModels.ForecastCityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.forecast_city_fragment.view.*

class ForecastCityFragment : Fragment() {

    private lateinit var viewModel: ForecastCityViewModel
    private val citiesAdapter by lazy {
        CitiesAdapter {
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
        }
    }

    private val citiesList = mutableListOf(
        CityModel(
            "1",
            "Warszawa",
            status = "Clear Sky",
            temperature = 15
        ),
        CityModel(
            "2",
            "Wrocław",
            status = "Clouds",
            temperature = 12
        ),
        CityModel(
            "3",
            "Poznań",
            status = "Clear Sky",
            temperature = 10
        ),
        CityModel(
            "4",
            "Gdańsk",
            status = "Clouds",
            temperature = 20
        ),
        CityModel(
            "5",
            "Los Angeles",
            status = "Sunny",
            temperature = 22
        ),
        CityModel(
            "6",
            "Miami",
            status = "Sunny",
            temperature = 30
        )
    )

    companion object {
        fun newInstance() =
            ForecastCityFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.forecast_city_fragment, container, false)
        rootView.rvCity.adapter = citiesAdapter.apply { submitList(citiesList) }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForecastCityViewModel::class.java)
        // TODO: Use the ViewModel

        val button: FloatingActionButton = view?.findViewById(R.id.addFAB2)!!
        button.setOnClickListener {
            findNavController().navigate(ForecastCityFragmentDirections.actionForecastCityFragmentToCitySearchFragment())
        }
    }

}
