package com.example.weatheracc.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatheracc.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.view.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

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
            addFAB.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.toSearchedCities())
            }
        }
    }
}
