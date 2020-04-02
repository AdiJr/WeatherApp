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
import com.example.weatheracc.viewModels.SavedCitiesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

    companion object {
        fun newInstance() =
            SavedCitiesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cities_saved_fragment, container, false).apply {
            rootView.rvCity.adapter = citiesAdapter
            viewModel.weatherList.observe(viewLifecycleOwner, Observer {
                citiesAdapter.submitList(it)
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val button: FloatingActionButton = view?.findViewById(R.id.addFAB2)!!
        button.setOnClickListener {
            findNavController().navigate(SavedCitiesFragmentDirections.actionSavedCitiesFragmentToSearchedCitiesFragment())
        }
    }

}