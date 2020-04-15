package com.example.weatheracc.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatheracc.R
import com.example.weatheracc.viewModels.SplashViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SplashFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<SplashViewModel> { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.splash_fragment, container, false).apply {
            if (checkInternetConnection(context)) {
                viewModel.fetchOnline()
                viewModel.proceed.observe(viewLifecycleOwner, Observer {
                    if (viewModel.isEmpty) {
                        findNavController().navigate(SplashFragmentDirections.toHome())
                    } else {
                        findNavController().navigate(SplashFragmentDirections.toSavedCities())
                    }
                })
            } else {
                viewModel.fetchOffline()
                viewModel.proceed.observe(viewLifecycleOwner, Observer {
                    if (viewModel.isEmpty) {
                        findNavController().navigate(SplashFragmentDirections.toHome())
                    } else {
                        findNavController().navigate(SplashFragmentDirections.toSavedCities())
                    }
                })
            }
        }
    }

    private fun checkInternetConnection(context: Context): Boolean {
        val conManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return (conManager.activeNetworkInfo != null && conManager.activeNetworkInfo.isAvailable
                && conManager.activeNetworkInfo.isConnected)
    }
}