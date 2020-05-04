package com.example.weatheracc.ui.fragments

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatheracc.R
import com.example.weatheracc.adapters.SavedCitiesAdapter
import com.example.weatheracc.models.Units
import com.example.weatheracc.utils.DetectConnection.checkInternetConnection
import com.example.weatheracc.viewModels.SavedCitiesViewModel
import com.google.android.gms.location.LocationServices
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.cities_saved_fragment.view.*
import kotlinx.android.synthetic.main.item_saved_city.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import javax.inject.Inject

class SavedCitiesFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @ExperimentalCoroutinesApi
    private val viewModel by viewModels<SavedCitiesViewModel> { factory }

    private val citiesAdapter by lazy {
        SavedCitiesAdapter {
            findNavController().navigate(SavedCitiesFragmentDirections.toDetailsFragment(it))
        }
    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            })
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
                    if (checkInternetConnection(context)) {
                        refreshData(
                            it.firstOrNull()!!.coordinates.lat,
                            it.firstOrNull()!!.coordinates.lon
                        )
                    } else {
                        getCityOffline(
                            it.firstOrNull()!!.coordinates.lat,
                            it.firstOrNull()!!.coordinates.lon
                        )
                    }
                    citiesAdapter.submitList(it)

                    val fusedLocationClient =
                        LocationServices.getFusedLocationProviderClient(activity!!)
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            val locale =
                                Locale.Builder().setLanguage(Locale.getDefault().language).build()
                            val geocoder = Geocoder(context, locale)
                            val geoCity =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)

                            for (i in it.indices) {
                                if (it[i].name == geoCity[0].locality) {
                                    rvCity[i].ivCurrentLocationIcon.visibility = View.VISIBLE
                                }
                                units.observe(viewLifecycleOwner, Observer { units ->
                                    tempUnitSwitcher.setText(getString(if (units == Units.METRIC) R.string.tempUnits_metric else R.string.tempUnits_imperial))

                                    if (it[i].main.temp > 28 && units == Units.METRIC) {
                                        rvCity[i].clSavedCityItem.setBackgroundResource(R.drawable.hot_background)
                                        rvCity[i].ivWeatherIcon.setImageDrawable(
                                            resources.getDrawable(
                                                R.drawable.icon_orange_sun
                                            )
                                        )
                                    }
                                    if (it[i].main.temp > 82.4 && units == Units.IMPERIAL) {
                                        rvCity[i].clSavedCityItem.setBackgroundResource(R.drawable.hot_background)
                                        rvCity[i].ivWeatherIcon.setImageDrawable(
                                            resources.getDrawable(
                                                R.drawable.icon_orange_sun
                                            )
                                        )
                                    }
                                })
                            }
                        }
                    }
                })
            }
        }
    }
}