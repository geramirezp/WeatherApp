package mx.com.hsbc.android.weather.presentation.weather

import mx.com.hsbc.android.weather.domain.entity.Weather
import mx.com.hsbc.android.weather.presentation.weather.model.WeatherModel


fun Weather.toMap() : WeatherModel {
    return WeatherModel(place, "$value $unit")
}
