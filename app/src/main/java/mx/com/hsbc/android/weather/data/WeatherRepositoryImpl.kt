package mx.com.hsbc.android.weather.data

import mx.com.hsbc.android.weather.data.datasource.local.WeatherLocalDataSource
import mx.com.hsbc.android.weather.data.datasource.toWeather
import mx.com.hsbc.android.weather.domain.FetchResult
import mx.com.hsbc.android.weather.domain.entity.Weather
import mx.com.hsbc.android.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherLocalDataSource: WeatherLocalDataSource) :
    WeatherRepository {
    override suspend fun getWeatherByIndex(index: Int): FetchResult<Weather> {
        return try {
            FetchResult.Success(weatherLocalDataSource.getWeatherByIndex(index).toWeather())
        } catch (e: Exception) {
            FetchResult.Error(e)
        }
    }

    override suspend fun getWeatherSize(): Int {
        return weatherLocalDataSource.getSize()
    }
}
