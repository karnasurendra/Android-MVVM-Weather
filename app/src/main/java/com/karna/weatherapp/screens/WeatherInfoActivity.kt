package com.karna.weatherapp.screens

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.karna.weatherapp.R
import com.karna.weatherapp.data.currentTemperatureModels.WeatherData
import com.karna.weatherapp.data.forecastTemperatureModels.ForecastItem
import com.karna.weatherapp.databinding.ActivityWeatherInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class WeatherInfoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityWeatherInfoBinding.inflate(layoutInflater)
    }
    private val snackBar by lazy {
        Snackbar.make(
            binding.main,
            getString(R.string.something_went_wrong),
            Snackbar.LENGTH_INDEFINITE
        )
    }
    private val slideUpAnim by lazy {
        AnimationUtils.loadAnimation(this, R.anim.slide_up)
    }
    private val viewModel: WeatherInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        listenToObservers()

        viewModel.getWeatherInformation()

    }

    private fun listenToObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.currentWeatherInfo.collectLatest {
                currentWeatherSuccessUiUpdate(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.nextFourDaysForecast.collectLatest { forecastList ->
                updateForecastWeatherInfo(forecastList)
                forecastWeatherSuccessUiUpdate()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.failureCase.collectLatest {
                if (!snackBar.isShown) {
                    binding.weatherLoadingProgressBar.visibility = View.GONE
                    snackBar.setAction(getString(R.string.retry)) {
                        binding.weatherLoadingProgressBar.visibility = View.VISIBLE
                        viewModel.getWeatherInformation()
                        snackBar.dismiss()
                    }.show()
                    snackBar.show()
                }
            }
        }

    }

    private fun currentWeatherSuccessUiUpdate(weatherData: WeatherData) {
        if (binding.weatherLoadingProgressBar.isVisible) {
            binding.weatherLoadingProgressBar.visibility = View.GONE
        }

        if (!binding.weatherDetailsIncludeLayout.weatherDetailsParentLayout.isVisible) {
            binding.weatherDetailsIncludeLayout.weatherDetailsParentLayout.visibility = View.VISIBLE
        }

        val currentTemperature =
            "${(viewModel.kelvinToCelsius(weatherData.mainInfo.temperature)).toInt()}${getString(R.string.degree_indicator)}"
        binding.weatherDetailsIncludeLayout.currentTemperature.text = currentTemperature
    }

    private fun forecastWeatherSuccessUiUpdate() {
        if (binding.weatherLoadingProgressBar.isVisible) {
            binding.weatherLoadingProgressBar.visibility = View.GONE
        }

        if (!binding.weatherDetailsIncludeLayout.weatherDetailsParentLayout.isVisible) {
            binding.weatherDetailsIncludeLayout.weatherDetailsParentLayout.visibility = View.VISIBLE
        }

        binding.weatherDetailsIncludeLayout.forecastLayout.visibility = View.VISIBLE
        binding.weatherDetailsIncludeLayout.forecastLayout.animation = slideUpAnim

    }

    private fun updateForecastWeatherInfo(forecastList: List<ForecastItem>) {
        forecastList.getOrNull(0)?.let { forecastItem ->
            val pair = viewModel.getDayAndTemperature(forecastItem)
            binding.weatherDetailsIncludeLayout.forecastDayOneTv.text = pair.first
            binding.weatherDetailsIncludeLayout.forecastDayOneTempTv.text = pair.second
        }
        forecastList.getOrNull(1)?.let { forecastItem ->
            val pair = viewModel.getDayAndTemperature(forecastItem)
            binding.weatherDetailsIncludeLayout.forecastDayTwoTv.text = pair.first
            binding.weatherDetailsIncludeLayout.forecastDayTwoTempTv.text = pair.second
        }
        forecastList.getOrNull(2)?.let { forecastItem ->
            val pair = viewModel.getDayAndTemperature(forecastItem)
            binding.weatherDetailsIncludeLayout.forecastDayThreeTv.text = pair.first
            binding.weatherDetailsIncludeLayout.forecastDayThreeTempTv.text = pair.second
        }
        forecastList.getOrNull(3)?.let { forecastItem ->
            val pair = viewModel.getDayAndTemperature(forecastItem)
            binding.weatherDetailsIncludeLayout.forecastDayFourTv.text = pair.first
            binding.weatherDetailsIncludeLayout.forecastDayFourTempTv.text = pair.second
        }
    }
}