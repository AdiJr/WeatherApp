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
import com.example.weatheracc.adapters.SavedCitiesAdapter
import com.example.weatheracc.models.CityModel
import com.example.weatheracc.viewModels.SavedCitiesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.cities_saved_fragment.view.*

class SavedCitiesFragment : Fragment() {

    private lateinit var viewModel: SavedCitiesViewModel
    private val citiesAdapter by lazy {
        SavedCitiesAdapter {
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
            SavedCitiesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.cities_saved_fragment, container, false)
        rootView.rvCity.adapter = citiesAdapter.apply { submitList(citiesList) }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SavedCitiesViewModel::class.java)
        // TODO: Use the ViewModel

        val button: FloatingActionButton = view?.findViewById(R.id.addFAB2)!!
        button.setOnClickListener {
            findNavController().navigate(SavedCitiesFragmentDirections.actionSavedCitiesFragmentToSearchedCitiesFragment())
        }
    }

}