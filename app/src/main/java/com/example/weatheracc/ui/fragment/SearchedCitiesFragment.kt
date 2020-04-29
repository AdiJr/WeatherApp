package com.example.weatheracc.ui.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.adapters.SearchedCitiesAdapter
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
            findNavController().navigate(SearchedCitiesFragmentDirections.toSavedCities())
        }
    }

    fun checkInternetConnection(context: Context): Boolean {
        val conManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return (conManager.activeNetworkInfo != null && conManager.activeNetworkInfo.isAvailable
                && conManager.activeNetworkInfo.isConnected)
    }

    private fun getCurrentCity() {
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
        }

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

            if (checkInternetConnection(context)) {
                lrCurrentCity.visibility = View.VISIBLE
                ivCurrentLocation.visibility = View.VISIBLE
                lrCurrentCity.setOnClickListener {
                    getCurrentCity()
                }
            } else {
                lrCurrentCity.visibility = View.GONE
                ivCurrentLocation.visibility = View.GONE
            }

            etSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (checkInternetConnection(context)) {
                        ivNoCon.visibility = View.GONE
                        tvNoCon.visibility = View.GONE
                        rvSearchedCities.visibility = View.VISIBLE
                        viewModel.searchCity(s.toString())
                    } else {
                        ivNoCon.visibility = View.VISIBLE
                        tvNoCon.visibility = View.VISIBLE
                        rvSearchedCities.visibility = View.GONE
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })

            ivBackArrow.setOnClickListener { findNavController().popBackStack() }
            rvSearchedCities.adapter = searchedCitiesAdapter

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