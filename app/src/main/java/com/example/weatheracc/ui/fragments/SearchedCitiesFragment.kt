package com.example.weatheracc.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.adapters.RecentCitiesAdapter
import com.example.weatheracc.adapters.SearchedCitiesAdapter
import com.example.weatheracc.models.WeatherForecast
import com.example.weatheracc.utils.DetectConnection.checkInternetConnection
import com.example.weatheracc.viewModels.SearchedCitiesViewModel
import com.google.android.gms.location.LocationServices
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.city_search_fragment.view.*
import java.util.*
import javax.inject.Inject

class SearchedCitiesFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<SearchedCitiesViewModel> { factory }

    private val searchedCitiesAdapter by lazy {
        SearchedCitiesAdapter {
            viewModel.storeCity(it)
            viewModel.hideKeyboard(context!!)
            findNavController().navigate(SearchedCitiesFragmentDirections.toSavedCities())
        }
    }

    private val recentCitiesAdapter by lazy {
        RecentCitiesAdapter {
            viewModel.storeCity(it)
            viewModel.hideKeyboard(context!!)
            findNavController().navigate(SearchedCitiesFragmentDirections.toSavedCities())
        }
    }

    private fun getCurrentCity() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                val locale = Locale.Builder().setLanguage(Locale.getDefault().language).build()
                val geocoder = Geocoder(context, locale)
                val geoCity = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                viewModel.searchCity(geoCity[0].locality)
                viewModel.cityList.observe(viewLifecycleOwner, Observer {
                    viewModel.storeCity(it[0])
                    findNavController().navigate(SearchedCitiesFragmentDirections.toSavedCities())
                })
            } else {
                Toast.makeText(
                    context,
                    "Location not available",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.city_search_fragment, container, false).apply {

            if (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    1
                )
            } else {
                lrCurrentCity.visibility = View.VISIBLE
                ivCurrentLocation.visibility = View.VISIBLE
                lrCurrentCity.setOnClickListener {
                    getCurrentCity()
                }
            }

            if (!checkInternetConnection(context)) {
                lrCurrentCity.visibility = View.GONE
                ivCurrentLocation.visibility = View.GONE
                rvRecents.visibility = View.GONE
            }

            etSearch.addTextChangedListener {
                if (checkInternetConnection(context)) {
                    ivNoCon.visibility = View.GONE
                    tvNoCon.visibility = View.GONE
                    rvSearchedCities.visibility = View.VISIBLE
                    rvRecents.visibility = View.GONE
                    viewModel.searchCity(it.toString())
                } else {
                    ivNoCon.visibility = View.VISIBLE
                    tvNoCon.visibility = View.VISIBLE
                    rvSearchedCities.visibility = View.GONE
                }
            }

            ivBackArrow.setOnClickListener { findNavController().popBackStack() }
            rvSearchedCities.adapter = searchedCitiesAdapter
            rvRecents.adapter = recentCitiesAdapter

            with(viewModel) {
                cityList.observe(viewLifecycleOwner, Observer {
                    if (it.isNotEmpty()) {
                        searchedCitiesAdapter.submitList(it)
                        handleVisibility(ivEmptyList, tvEmptyList, rvSearchedCities, false)
                    } else {
                        handleVisibility(ivEmptyList, tvEmptyList, rvSearchedCities, true)
                    }
                })
                errorMessage.observe(viewLifecycleOwner, Observer {
                    handleVisibility(ivEmptyList, tvEmptyList, rvSearchedCities, true)
                })

                getRecents()
                recents.observe(viewLifecycleOwner, Observer {
                    if (it.isNotEmpty()) {
                        rvRecents.visibility = View.VISIBLE
                        val recentsList = mutableListOf<WeatherForecast>()
                        recentsList.addAll(it)
                        recentCitiesAdapter.submitList(recentsList)
                    } else {
                        rvRecents.visibility = View.GONE
                    }
                })
            }
        }
    }

    private fun handleVisibility(
        imageView: ImageView,
        textView: TextView,
        recyclerView: RecyclerView,
        shouldShowError: Boolean
    ) {
        textView.visibility = if (shouldShowError) View.VISIBLE else View.GONE
        imageView.visibility = if (shouldShowError) View.VISIBLE else View.GONE
        recyclerView.visibility = if (shouldShowError) View.GONE else View.VISIBLE
    }
}