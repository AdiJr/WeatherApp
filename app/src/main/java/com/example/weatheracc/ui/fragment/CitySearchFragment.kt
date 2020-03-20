package com.example.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.weatheracc.R
import com.example.weatheracc.adapters.SearchedCitiesAdapter
import com.example.weatheracc.models.SearchedCityModel
import kotlinx.android.synthetic.main.city_search_fragment.view.*

class CitySearchFragment : Fragment() {

    private val searchedCitiesAdapter by lazy {
        SearchedCitiesAdapter {
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
        }
    }

    private val citiesList = mutableListOf(
        SearchedCityModel(
            "1",
            "Warszawa"
        ),
        SearchedCityModel(
            "2",
            "Wrocław"
        ),
        SearchedCityModel(
            "3",
            "Poznań"
        ),
        SearchedCityModel(
            "4",
            "Gdańsk"
        ),
        SearchedCityModel(
            "5",
            "Los Angeles"
        ),
        SearchedCityModel(
            "6",
            "Miami"
        )
    )

    companion object {
        fun newInstance() = CitySearchFragment()
    }

    private lateinit var viewModel: CitySearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.city_search_fragment, container, false)
        rootView.rvSearchedCities.adapter = searchedCitiesAdapter.apply { submitList(citiesList) }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CitySearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}