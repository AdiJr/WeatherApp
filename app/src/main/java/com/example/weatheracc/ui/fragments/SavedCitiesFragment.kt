package com.example.weatheracc.ui.fragments

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatheracc.R
import com.example.weatheracc.adapters.SavedCitiesAdapter
import com.example.weatheracc.models.Units
import com.example.weatheracc.models.WeatherForecast
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

    @ExperimentalStdlibApi
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
            rvCity.adapter = citiesAdapter

            tempUnitSwitcher.setOnClickListener {
                viewModel.updateUnits()
            }

            fabAddCity.setOnClickListener {
                findNavController().navigate(SavedCitiesFragmentDirections.toSearchedCities())
            }

            with(viewModel) {
                weatherList.observe(
                    viewLifecycleOwner,
                    Observer { weatherList: List<WeatherForecast> ->
                        if (checkInternetConnection(context)) {
                            refreshData(
                                weatherList.firstOrNull()!!.coordinates.lat,
                                weatherList.firstOrNull()!!.coordinates.lon
                            )
                        } else {
                            getCityOffline(
                                weatherList.firstOrNull()!!.coordinates.lat,
                                weatherList.firstOrNull()!!.coordinates.lon
                            )
                        }
                        citiesAdapter.submitList(weatherList)

                        val fusedLocationClient =
                            LocationServices.getFusedLocationProviderClient(activity!!)
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            if (location != null) {
                                val locale =
                                    Locale.Builder().setLanguage(Locale.getDefault().language)
                                        .build()
                                val geocoder = Geocoder(context, locale)
                                val geoCity =
                                    geocoder.getFromLocation(
                                        location.latitude,
                                        location.longitude,
                                        1
                                    )

                                for (i in weatherList.indices) {
                                    rvCity[i].setOnLongClickListener {
                                        ivRemove.visibility = View.VISIBLE
                                        rvCity[i].rbSelected.visibility = View.VISIBLE
                                        rvCity[i].rbSelected.isChecked = true

                                        rvCity[i].setOnClickListener {
                                            rvCity[i].setOnClickListener {
                                                rvCity[i].rbSelected.isChecked =
                                                    !rvCity[i].rbSelected.isChecked
                                            }
                                        }

                                        ivRemove.setOnClickListener {
                                            if (rvCity[i].rbSelected.isChecked) {
                                                val list = weatherList.toMutableList()
                                                list.removeAt(i)
                                                viewModel.removeItem(weatherList[i])
                                                citiesAdapter.submitList(list.toList())
                                                Toast.makeText(
                                                    context,
                                                    "Item removed",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                ivRemove.visibility = View.GONE
                                            }
                                        }
                                        return@setOnLongClickListener true
                                    }

                                    if (weatherList[i].name == geoCity[0].locality) {
                                        rvCity[i].ivCurrentLocationIcon.visibility = View.VISIBLE
                                    }

                                    units.observe(viewLifecycleOwner, Observer { units ->
                                        tempUnitSwitcher.setText(getString(if (units == Units.METRIC) R.string.tempUnits_metric else R.string.tempUnits_imperial))

                                        if (weatherList[i].main.temp > 28 && units == Units.METRIC) {
                                            rvCity[i].clSavedCityItem.setBackgroundResource(R.drawable.hot_background)
                                            rvCity[i].ivWeatherIcon.setImageDrawable(
                                                resources.getDrawable(
                                                    R.drawable.icon_orange_sun
                                                )
                                            )
                                        }
                                        if (weatherList[i].main.temp > 82.4 && units == Units.IMPERIAL) {
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