package com.example.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatheracc.R
import com.example.weatheracc.models.Units
import com.example.weatheracc.viewModels.HomeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.view.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<HomeViewModel> { factory }

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
        return inflater.inflate(R.layout.home_fragment, container, false).apply {
            tempUnitSwitcher.setOnClickListener {
                viewModel.updateUnits()
            }
            addFAB.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.toSearchedCities())
            }
            with(viewModel) {
                units.observe(viewLifecycleOwner, Observer {
                    tempUnitSwitcher.setText(getString(if (it == Units.METRIC) R.string.tempUnits_metric else R.string.tempUnits_imperial))
                })
            }
        }
    }
}
