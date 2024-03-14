package mx.com.hsbc.android.weather.data

import kotlinx.coroutines.test.runTest
import mx.com.hsbc.android.weather.data.datasource.local.WeatherLocalDataSource
import mx.com.hsbc.android.weather.data.datasource.local.WeatherLocalDataSourceImpl
import mx.com.hsbc.android.weather.data.datasource.local.entity.WeatherDataLocal
import mx.com.hsbc.android.weather.domain.FetchResult
import mx.com.hsbc.android.weather.domain.entity.Weather
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class WeatherRepositoryImplTest {
    private lateinit var  weatherRepositoryImpl: WeatherRepositoryImpl
    private lateinit var  weatherLocalDataSource : WeatherLocalDataSource
    private val weatherDataLocal = WeatherDataLocal("Hong Kong Observatory", "C" ,26)
    private val listWeatherDataLocal = mutableListOf(weatherDataLocal)
    @Before
    fun setUp() {
        weatherLocalDataSource = mock(WeatherLocalDataSourceImpl::class.java)
        weatherRepositoryImpl =  WeatherRepositoryImpl(weatherLocalDataSource)
    }

    @Test
    fun getWeatherByIndex() = runTest {
        val weather = weatherByIndex(0)
        assert(weather.place == weatherDataLocal.place)
        assert(weather.value == weatherDataLocal.value)
        assert(weather.unit == weatherDataLocal.unit)
    }

    private suspend fun weatherByIndex(index : Int) : Weather {
        `when`(weatherLocalDataSource.getWeatherByIndex(index)).thenReturn(weatherDataLocal)
        val result = weatherRepositoryImpl.getWeatherByIndex(index)
        assert(result is FetchResult.Success)
        return (result as FetchResult.Success<Weather>).data
    }

    @Test
    fun getWeatherByIndexWithIndexGreaterThatSizeList() = runTest {
        listWeatherDataLocal.add(WeatherDataLocal( "Shau Kei Wan", "C" ,25))
        listWeatherDataLocal.add(WeatherDataLocal( "Sha Tin", "C" ,22))
        val weather = weatherByIndex(3)
        val index = 3%listWeatherDataLocal.size
        assert(weather.place == listWeatherDataLocal[index].place)
        assert(weather.value == listWeatherDataLocal[index].value)
        assert(weather.unit == listWeatherDataLocal[index].unit)
    }

    @Test
    fun getWeatherSize() = runTest {
        `when`(weatherLocalDataSource.getSize()).thenReturn(listWeatherDataLocal.size)
        val size = weatherRepositoryImpl.getWeatherSize()
        assert(size == listWeatherDataLocal.size)
    }

    @Test
    fun getWeatherByIndexWithFailure() = runTest {
        val result = weatherRepositoryImpl.getWeatherByIndex(0)
        assert(result is FetchResult.Error)
    }
}