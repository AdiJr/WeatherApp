package com.example.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheracc.R
import com.example.weatheracc.adapters.SearchedCitiesAdapter
import com.example.weatheracc.viewModels.SearchedCitiesViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.city_search_fragment.view.*
import javax.inject.Inject

class SearchedCitiesFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<SearchedCitiesViewModel> { factory }

    private val searchedCitiesAdapter by lazy {
        SearchedCitiesAdapter {
            viewModel.storeCity(it)
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.city_search_fragment, container, false).apply {
            btnConfirm.setOnClickListener { viewModel.searchCity(etSearch.text.toString()) }
            rvSearchedCities.adapter = searchedCitiesAdapter

            with(viewModel) {
                cityList.observe(viewLifecycleOwner, Observer {
                    if (it.isNotEmpty()) {
                        searchedCitiesAdapter.submitList(it)
                        handleVisibility(tvEmptyList, rvSearchedCities, false)
                    } else {
                        tvEmptyList.text = context.getString(R.string.list_empty)
                        handleVisibility(tvEmptyList, rvSearchedCities, true)
                    }
                })
                errorMessage.observe(viewLifecycleOwner, Observer {
                    tvEmptyList.text = it
                    handleVisibility(tvEmptyList, rvSearchedCities, true)
                })
            }
        }
    }

    private fun handleVisibility(
        textView: TextView,
        recyclerView: RecyclerView,
        shouldShowError: Boolean
    ) {
        textView.visibility = if (shouldShowError) View.VISIBLE else View.GONE
        recyclerView.visibility = if (shouldShowError) View.GONE else View.VISIBLE
    }
}