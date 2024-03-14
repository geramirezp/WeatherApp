package mx.com.hsbc.android.weather.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mx.com.hsbc.android.weather.domain.entity.Weather
import mx.com.hsbc.android.weather.domain.repository.WeatherRepository
import javax.inject.Inject
import kotlin.random.Random

class GetRandomWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): FetchResult<Weather> = withContext(dispatcher) {
        val max = try {
            weatherRepository.getWeatherSize()
        } catch (e: Exception) {
            return@withContext FetchResult.Error(e)
        }
        val index = Random.nextInt(max)
        weatherRepository.getWeatherByIndex(index)
    }
}
