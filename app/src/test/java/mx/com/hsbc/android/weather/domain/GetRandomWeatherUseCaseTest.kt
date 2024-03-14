package mx.com.hsbc.android.weather.domain

import kotlinx.coroutines.test.*
import mx.com.hsbc.android.weather.domain.entity.Weather
import mx.com.hsbc.android.weather.domain.repository.WeatherRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetRandomWeatherUseCaseTest {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var testCoroutineScheduler: TestCoroutineScheduler
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope

    @Before
    fun setUp() {
        testCoroutineScheduler = TestCoroutineScheduler()
        testDispatcher = StandardTestDispatcher(testCoroutineScheduler)
        testScope = TestScope(testDispatcher)
        weatherRepository = mock(WeatherRepository::class.java)
    }

    @Test
    fun testInvokeSuccess() = testScope.runTest {
        val mockWeather = Weather("MX", 25, "C")
        val expectedResult = FetchResult.Success(mockWeather)
        `when`(weatherRepository.getWeatherSize()).thenReturn(5)
        `when`(weatherRepository.getWeatherByIndex(anyInt())).thenReturn(FetchResult.Success(mockWeather))
        val useCase = GetRandomWeatherUseCase(weatherRepository, testDispatcher)
        val result = useCase()

        assertEquals(expectedResult, result)
        verify(weatherRepository).getWeatherSize()
        verify(weatherRepository).getWeatherByIndex(anyInt())
    }

    @Test
    fun testInvokeError() = testScope.runTest {

        `when`(weatherRepository.getWeatherSize()).thenReturn(null)
        val getRandomWeatherUseCase = GetRandomWeatherUseCase(weatherRepository, testDispatcher)

        val result = getRandomWeatherUseCase()
        assert(result is FetchResult.Error)
    }

    @Test
    fun testInvokeErrorByGetWeather() = testScope.runTest {
        `when`(weatherRepository.getWeatherSize()).thenReturn(5)
        val exception =Exception("Error occurred")
        `when`(weatherRepository.getWeatherByIndex(anyInt())).thenReturn(FetchResult.Error(exception))
        val getRandomWeatherUseCase = GetRandomWeatherUseCase(weatherRepository, testDispatcher)

        val result = getRandomWeatherUseCase()
        assert(result is FetchResult.Error)

        assertEquals(exception, (result as FetchResult.Error).error)
    }

}
