package com.example.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.weatheracc.viewModels.DetailsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.details_fragment.view.*
import kotlinx.android.synthetic.main.item_daily_forecast.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class DetailsFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<DetailsViewModel> { factory }
    private val args by navArgs<DetailsFragmentArgs>()
    private val detailsAdapter by lazy {
        DailyAdapter {
            clContainer.setBackgroundResource(R.drawable.item_forecast_white)
        }
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

            rvDailyForecast.adapter = detailsAdapter
            rvDetailsHourly.adapter = hourlyAdapter
            rvDetailsCurrent.adapter = currentAdapter

            with(viewModel) {
                storeCityByCoordinates(args.item.coordinates.lat, args.item.coordinates.lon)
                dailyList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    detailsAdapter.submitList(it)
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
                    tvDetailsDescription.text =
                        "${it.current.weather.firstOrNull()!!.description.substring(0, 1)
                            .toUpperCase() + args.item.weather.firstOrNull()!!.description.substring(
                            1
                        ).toLowerCase()}"
                    tvDetailsTempMaxMin.text =
                        "${args.item.main.temp_max.roundToInt()}\u00B0 / ${args.item.main.temp_min.roundToInt()}\u00B0"
                    currentAdapter.submitList(forecastDetails)

                    when (it.current.weather.firstOrNull()!!.main) {
                        "Rain" -> {
                            ivDetailsSun.visibility = View.GONE
                            ivClouds.visibility = View.VISIBLE
                            ivRain.visibility = View.VISIBLE
                            ivGradient.visibility = View.VISIBLE
                            ivGradient.setImageDrawable(resources.getDrawable(R.drawable.gradient_rain))
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
                            ivRain.visibility = View.GONE
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
                            ivDetailsSun.visibility = View.GONE
                            ivClouds.visibility = View.VISIBLE
                            ivRain.visibility = View.GONE
                            ivGradient.visibility = View.VISIBLE
                            ivGradient.setImageDrawable(resources.getDrawable(R.drawable.gradient_mist))
                            if (System.currentTimeMillis() > it.current.sunset && System.currentTimeMillis() < it.current.sunrise) {
                                ivDetailsSun.visibility = View.GONE
                                ivGradient.setImageDrawable(resources.getDrawable(R.drawable.gradient_sunset))
                                ivStars.visibility = View.VISIBLE
                            }
                        }
                    }

                    if (tvDetailsDescription.text == "Few clouds") {
                        ivDetailsSun.visibility = View.GONE
                        ivClouds.visibility = View.VISIBLE
                        ivRain.visibility = View.GONE
                        ivGradient.visibility = View.GONE
                        if (System.currentTimeMillis() > it.current.sunset && System.currentTimeMillis() < it.current.sunrise) {
                            ivDetailsSun.visibility = View.GONE
                            ivClouds.visibility = View.VISIBLE
                            ivGradient.setImageDrawable(resources.getDrawable(R.drawable.gradient_sunset))
                            ivStars.visibility = View.VISIBLE
                        }
                    }

                    if (System.currentTimeMillis() > it.current.sunset && System.currentTimeMillis() < it.current.sunrise) {
                        ivDetailsSun.visibility = View.GONE
                        ivGradient.visibility = View.VISIBLE
                        ivGradient.setImageDrawable(resources.getDrawable(R.drawable.gradient_sunset))
                        ivStars.visibility = View.VISIBLE
                    }

                    if (it.current.temp.roundToInt() > 28 && currentUnits == Units.METRIC) {
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

    fun search(v: View) {
        findNavController().navigate(DetailsFragmentDirections.toSearchedCitiesFragment())
    }
}