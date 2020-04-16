package com.example.weatheracc.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
import kotlinx.android.synthetic.main.item_saved_city.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlin.math.roundToInt

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

    private fun checkInternetConnection(context: Context): Boolean {
        val conManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return (conManager.activeNetworkInfo != null && conManager.activeNetworkInfo.isAvailable
                && conManager.activeNetworkInfo.isConnected)
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
                        Toast.makeText(context, "No Internet connection", Toast.LENGTH_SHORT).show()
                        getCityOffline(
                            it.firstOrNull()!!.coordinates.lat,
                            it.firstOrNull()!!.coordinates.lon
                        )
                    }

                    citiesAdapter.submitList(it)

                    if (it.firstOrNull()!!.main.temp.roundToInt() > 28 && currentUnits == Units.METRIC) {
                        clSavedCityItem.setBackgroundResource(R.drawable.hot_background)
                        weatherIcon.setImageDrawable(resources.getDrawable(R.drawable.icon_orange_sun))
                    }
                    if (it.firstOrNull()!!.main.temp > 82.4 && currentUnits == Units.IMPERIAL) {
                        clSavedCityItem.setBackgroundResource(R.drawable.hot_background)
                        weatherIcon.setImageDrawable(resources.getDrawable(R.drawable.icon_orange_sun))
                    }

                })
                units.observe(viewLifecycleOwner, Observer {
                    tempUnitSwitcher.setText(getString(if (it == Units.METRIC) R.string.tempUnits_metric else R.string.tempUnits_imperial))
                })
            }
        }
    }
}