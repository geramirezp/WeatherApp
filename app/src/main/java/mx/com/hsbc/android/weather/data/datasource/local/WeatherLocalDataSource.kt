package mx.com.hsbc.android.weather.data.datasource.local

import kotlinx.coroutines.flow.Flow
import mx.com.hsbc.android.weather.data.datasource.local.entity.WeatherDataLocal
import mx.com.hsbc.android.weather.data.datasource.local.entity.WeathersLocal

interface WeatherLocalDataSource {
    fun getAll() : Flow<WeathersLocal>
    suspend fun getSize() : Int
    suspend fun getWeatherByIndex(index : Int) : WeatherDataLocal
}
