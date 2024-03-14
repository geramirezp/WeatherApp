package mx.com.hsbc.android.weather.data.datasource.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import mx.com.hsbc.android.weather.data.datasource.AssetLoader
import mx.com.hsbc.android.weather.data.datasource.local.entity.WeatherDataLocal
import mx.com.hsbc.android.weather.data.datasource.local.entity.WeathersLocal
import javax.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(
    private val assetLoader: AssetLoader,
    private val idRaw: Int,
    scope: CoroutineScope
) : WeatherLocalDataSource {

    private val weathersLocal = scope.async { assetLoader.getFileDataById(idRaw) }

    override fun getAll(): Flow<WeathersLocal> = channelFlow {
        val weathers = try {
            weathersLocal.await()
        } catch (e: Exception) {
            close(e)
            return@channelFlow
        }
        send(weathers)
    }

    override suspend fun getWeatherByIndex(index: Int): WeatherDataLocal {
         return try {
             weathersLocal.await().weathers[index]
        } catch (e: IndexOutOfBoundsException) {
             weathersLocal.await().weathers.let { it[index % it.size] }
        } catch (e: Exception) {
             throw e
        }
    }

    override suspend fun getSize(): Int {
       return weathersLocal.await().weathers.size
    }
}
