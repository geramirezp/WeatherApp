package mx.com.hsbc.android.weather.domain.repository

import mx.com.hsbc.android.weather.domain.FetchResult
import mx.com.hsbc.android.weather.domain.entity.Weather

interface WeatherRepository {
    suspend fun getWeatherByIndex(index: Int): FetchResult<Weather>
    suspend fun getWeatherSize(): Int
}
