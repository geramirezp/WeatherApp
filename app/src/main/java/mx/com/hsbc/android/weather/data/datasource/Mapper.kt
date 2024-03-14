package mx.com.hsbc.android.weather.data.datasource

import mx.com.hsbc.android.weather.data.datasource.local.entity.WeatherDataLocal
import mx.com.hsbc.android.weather.domain.entity.Weather

fun WeatherDataLocal.toWeather() : Weather {
    return Weather(place, value, unit)
}
