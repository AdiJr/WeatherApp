package com.example.weatheracc.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatheracc.BuildConfig
import com.example.weatheracc.R
import com.example.weatheracc.adapters.CurrentConditionsAdapter
import com.example.weatheracc.adapters.DailyAdapter
import com.example.weatheracc.adapters.HourlyAdapter
import com.example.weatheracc.models.ForecastDetails
import com.example.weatheracc.models.Units
import com.example.weatheracc.utils.DetectConnection.checkInternetConnection
import com.example.weatheracc.viewModels.DetailsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.details_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class DetailsFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<DetailsViewModel> { factory }
    private val args by navArgs<DetailsFragmentArgs>()
    private val dailyAdapter by lazy {
        DailyAdapter()
    }
    private val hourlyAdapter by lazy {
        HourlyAdapter()
    }
    private val currentAdapter by lazy {
        CurrentConditionsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false).apply {

            rvDailyForecast.adapter = dailyAdapter
            rvDetailsHourly.adapter = hourlyAdapter
            rvDetailsCurrent.adapter = currentAdapter

            with(viewModel) {
                swipeRefresh.setOnRefreshListener {
                    if (checkInternetConnection(context)) {
                        getCityOnline(args.item.coordinates.lat, args.item.coordinates.lon)
                        swipeRefresh.isRefreshing = false
                        Toast.makeText(context, "Forecast updated", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                        swipeRefresh.isRefreshing = false
                    }
                }

                if (checkInternetConnection(context)) {
                    getCityOnline(args.item.coordinates.lat, args.item.coordinates.lon)
                } else {
                    getCityOffline(args.item.coordinates.lat, args.item.coordinates.lon)
                }

                dailyList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    it.removeAt(0)
                    dailyAdapter.submitList(it)
                })

                hourlyList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    hourlyAdapter.submitList(it)
                })

                allList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val date = it.current.dt.toLong() * 1000
                    val locale = Locale("", args.item.sys.country)
                    val countryName = locale.displayName
                    val timeFormatter = SimpleDateFormat("HH:mm", Locale(args.item.sys.country))
                    val timeUnixSunrise = it.current.sunrise.toLong() * 1000
                    val timeSunrise: String = timeFormatter.format(timeUnixSunrise)
                    val timeUnixSunset = it.current.sunset.toLong() * 1000
                    val timeSunset: String = timeFormatter.format(timeUnixSunset)

                    val forecastDetails = mutableListOf(
                        ForecastDetails(
                            "Sunrise",
                            timeSunrise
                        ),
                        ForecastDetails(
                            "Sunset",
                            timeSunset
                        ),
                        ForecastDetails(
                            "Cloudiness",
                            it.current.clouds.toString() + "%"
                        ),
                        ForecastDetails(
                            "Humidity",
                            it.current.humidity.toString() + "%"
                        ),
                        ForecastDetails(
                            "Wind speed",
                            it.current.wind_speed.roundToInt().toString() + " m/s"
                        ),
                        ForecastDetails(
                            "Feels like",
                            it.current.feels_like.roundToInt().toString() + "\u00B0"
                        ),
                        ForecastDetails(
                            "Pressure",
                            it.current.pressure.toString() + " hPa"
                        ),
                        ForecastDetails(
                            "Visibility",
                            (it.current.visibility / 1000).toString() + " km"
                        ),
                        ForecastDetails(
                            "UV index",
                            it.current.uvi.roundToInt().toString()
                        )
                    )

                    tvDetailsDate.text = ""
                    tvLastUpdated.text = "Last updated: " + sdf.format(date)
                    tvDetailsCityName.text = "${args.item.name}, $countryName"
                    tvDetailsTemp.text = "${args.item.main.temp.roundToInt()}\u00B0"
                    tvDetailsDescription.text = args.item.weather.firstOrNull()!!.description
                    tvDetailsTempMaxMin.text =
                        "${args.item.main.temp_max.roundToInt()}\u00B0 / ${args.item.main.temp_min.roundToInt()}\u00B0"
                    currentAdapter.submitList(forecastDetails)

                    if (System.currentTimeMillis() > it.current.sunrise) {
                        when (it.current.weather.firstOrNull()!!.main) {
                            "Rain" -> {
                                ivDetailsSun.visibility = View.GONE
                                ivClouds.visibility = View.VISIBLE
                                ivRain.visibility = View.VISIBLE
                            }
                            "Mist" -> {
                                ivDetailsSun.visibility = View.GONE
                                ivClouds.visibility = View.VISIBLE
                                ivRain.visibility = View.GONE
                                ivGradient.visibility = View.VISIBLE
                            }
                            "Snow" -> {
                                ivDetailsSun.visibility = View.GONE
                                ivClouds.visibility = View.VISIBLE
                                ivRain.visibility = View.VISIBLE
                                ivGradient.visibility = View.GONE
                                ivRain.setImageDrawable(resources.getDrawable(R.drawable.snow))
                            }
                            "Thunderstorm" -> {
                                ivDetailsSun.visibility = View.GONE
                                ivClouds.visibility = View.VISIBLE
                                ivRain.visibility = View.VISIBLE
                                ivRain.setImageDrawable(resources.getDrawable(R.drawable.thunder_and_rain))
                            }
                            "Clouds" -> {
                                ivGradient.visibility = View.GONE
                                ivClouds.visibility = View.VISIBLE
                            }
                        }
                    } else {
                        if (System.currentTimeMillis() > it.current.sunset) {
                            ivDetailsSun.visibility = View.GONE
                            ivGradient.visibility = View.VISIBLE
                            ivGradient.setImageDrawable(resources.getDrawable(R.drawable.gradient_sunset))
                            ivStars.visibility = View.VISIBLE
                        }
                    }

                    if (it.current.weather.firstOrNull()!!.description == "overcast clouds") {
                        ivDetailsSun.visibility = View.GONE
                        ivClouds.visibility = View.VISIBLE
                        ivRain.visibility = View.GONE
                        ivGradient.visibility = View.VISIBLE
                        ivGradient.setImageDrawable(resources.getDrawable(R.drawable.gradient_mist))
                    }

                    if (it.current.temp > 28 && currentUnits == Units.METRIC) {
                        ivGradient.visibility = View.VISIBLE
                        ivGradient.setImageDrawable(resources.getDrawable(R.drawable.gradient_hot))
                        ivDetailsSun.setImageDrawable(resources.getDrawable(R.drawable.sun_orange))
                    }

                    if (it.current.temp > 82.4 && currentUnits == Units.IMPERIAL) {
                        ivGradient.visibility = View.VISIBLE
                        ivGradient.setImageDrawable(resources.getDrawable(R.drawable.gradient_hot))
                        ivDetailsSun.setImageDrawable(resources.getDrawable(R.drawable.sun_orange))
                    }
                })
            }

            ivBackArrowDetails.setOnClickListener {
                findNavController().popBackStack()
            }
            ivDetailsSearch.setOnClickListener {
                findNavController().navigate(DetailsFragmentDirections.toSearchedCitiesFragment())
            }

            tvFooterAppDetails.text =
                resources.getString(R.string.app_name) + ", version: ${BuildConfig.VERSION_NAME}"
            tvFooterCopyright.text = "Copyright \u00A9 2020 AdiJr, Mobilabs, Accenture"
        }
    }
}