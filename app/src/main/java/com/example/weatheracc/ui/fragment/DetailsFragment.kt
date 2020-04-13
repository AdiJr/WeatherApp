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
import com.example.weatheracc.adapters.DetailsAdapter
import com.example.weatheracc.adapters.HourlyAdapter
import com.example.weatheracc.models.ForecastDetails
import com.example.weatheracc.models.HoursDetails
import com.example.weatheracc.models.TestOneCallAPi
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
        DetailsAdapter {
            clContainer.setBackgroundResource(R.drawable.item_forecast_white)
        }
    }
    private val hourlyAdapter by lazy {
        HourlyAdapter()
    }
    private val currentAdapter by lazy {
        CurrentConditionsAdapter()
    }

    private val testList = mutableListOf(
        TestOneCallAPi(
            "1",
            "Tuesday",
            20
        ),
        TestOneCallAPi(
            "2",
            "Wednesday",
            15
        ),
        TestOneCallAPi(
            "3",
            "Thursday",
            25
        ),
        TestOneCallAPi(
            "4",
            "Friday",
            13
        ),
        TestOneCallAPi(
            "5",
            "Saturday",
            28
        )
    )

    private val testListHourly = mutableListOf(
        HoursDetails(
            "9:00",
            "Sunny",
            12,
            false
        ),
        HoursDetails(
            "12:00",
            "Cloudy",
            19,
            false
        ),
        HoursDetails(
            "15:00",
            "Windy",
            25,
            false
        ),
        HoursDetails(
            "18:00",
            "Rainy",
            18,
            false
        ),
        HoursDetails(
            "21:00",
            "Sunny",
            20,
            false
        ),
        HoursDetails(
            "24:00",
            "Cloudy",
            14,
            true
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false).apply {
            /* val city = viewModel.storeCityByCoordinates(
                 args.item.coordinates.lat,
                 args.item.coordinates.lon
             )*/
            toolbar.inflateMenu(R.menu.menu_details)
            ivBackArrowDetails.setOnClickListener {
                findNavController().popBackStack()
            }

            val sdf = SimpleDateFormat("HH:mm")
            val date = args.item.dt.toLong() * 1000
            val locale = Locale("", args.item.sys.country)
            val countryName = locale.displayName

            tvDetailsDate.text = ""
            tvLastUpdated.text = "Last updated: " + sdf.format(date)
            tvDetailsCityName.text = "${args.item.name}, $countryName"
            tvDetailsTemp.text = "${args.item.main.temp.roundToInt()}\u00B0"
            tvDetailsDescription.text =
                "Current conditions: ${args.item.weather.firstOrNull()!!.description}"
            tvDetailsTempMaxMin.text =
                "${args.item.main.temp_max.roundToInt()}\u00B0 / ${args.item.main.temp_min.roundToInt()}\u00B0"

            rvDailyForecast.adapter = detailsAdapter
            detailsAdapter.submitList(testList)

            rvDetailsHourly.adapter = hourlyAdapter
            hourlyAdapter.submitList(testListHourly)

            val timeFormatter = SimpleDateFormat("HH:mm")
            val timeUnixSunrise = args.item.sys.sunrise.toLong() * 1000
            val timeSunrise: String = timeFormatter.format(timeUnixSunrise)
            val timeUnixSunset = args.item.sys.sunset.toLong() * 1000
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
                    "Chance of rain",
                    "10%"
                ),
                ForecastDetails(
                    "Humidity",
                    args.item.main.humidity.toString() + "%"
                ),
                ForecastDetails(
                    "Wind speed",
                    args.item.wind.speed.toString() + " m/s"
                ),
                ForecastDetails(
                    "Feels like",
                    args.item.main.feels_like.roundToInt().toString() + "\u00B0"
                ),
                ForecastDetails(
                    "Pressure",
                    args.item.main.pressure.toString() + " hPa"
                ),
                ForecastDetails(
                    "Visibility",
                    (args.item.visibility / 1000).toString() + " km"
                ),
                ForecastDetails(
                    "UV index",
                    "2"
                )
            )

            rvDetailsCurrent.adapter = currentAdapter
            currentAdapter.submitList(forecastDetails)
            tvFooterAppDetails.text =
                resources.getString(R.string.app_name) + ", version: ${BuildConfig.VERSION_NAME}"
            tvFooterCopyright.text = "Copyright \u00A9 by AdiJr, Mobilabs, Accenture"
        }
    }
}