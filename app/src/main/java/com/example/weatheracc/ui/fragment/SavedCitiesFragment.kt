package com.example.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatheracc.R
import com.example.weatheracc.adapters.SavedCitiesAdapter
import com.example.weatheracc.models.Units
import com.example.weatheracc.viewModels.SavedCitiesViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.cities_saved_fragment.view.*
import javax.inject.Inject

class SavedCitiesFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<SavedCitiesViewModel> { factory }

    private val citiesAdapter by lazy {
        SavedCitiesAdapter {
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cities_saved_fragment, container, false).apply {
            tempUnitSwitcher.setOnClickListener {
                viewModel.updateUnits()
            }
            rvCity.adapter = citiesAdapter
            fabAddCity.setOnClickListener {
                findNavController().navigate(SavedCitiesFragmentDirections.toSearchedCities())
            }

            with(viewModel) {
                weatherList.observe(viewLifecycleOwner, Observer {
                    citiesAdapter.submitList(it)
                })
                units.observe(viewLifecycleOwner, Observer {
                    tempUnitSwitcher.setText(getString(if (it == Units.METRIC) R.string.tempUnits_metric else R.string.tempUnits_imperial))
                })
            }
        }
    }
}