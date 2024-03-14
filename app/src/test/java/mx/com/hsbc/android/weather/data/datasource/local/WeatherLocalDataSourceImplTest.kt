package mx.com.hsbc.android.weather.data.datasource.local

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.*
import mx.com.hsbc.android.weather.data.datasource.AssetLoader
import mx.com.hsbc.android.weather.data.datasource.local.entity.WeatherDataLocal
import mx.com.hsbc.android.weather.data.datasource.local.entity.WeathersLocal
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito.mock
import kotlin.random.Random

class WeatherLocalDataSourceImplTest {
    private lateinit var weatherLocalDataSource: WeatherLocalDataSourceImpl
    private lateinit var weatherLocalDataSourceError: WeatherLocalDataSourceImpl
    private lateinit var weathersLocal : WeathersLocal
    private lateinit var assetLoader : AssetLoader
    private lateinit var assetLoaderError : AssetLoader
    private val scope = TestScope()
    private val scopeError = TestScope()
    private val idRaw = 1
    private val idRaw2 = 2

    @Before
    fun setup() {
        assetLoader = mock(AssetLoader::class.java)
        assetLoaderError = mock(AssetLoader::class.java)
        weathersLocal = WeathersLocal(listOf(
            WeatherDataLocal( "Hong Kong Par","C",25),
            WeatherDataLocal( "Shau Kei Wan", "C",25),
            WeatherDataLocal( "Kowloon City", "C",25),
            WeatherDataLocal( "Happy Valley", "C",26),
            WeatherDataLocal( "Wong Tai Sin", "C",26),
        ),"")
        Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))
        scope.launch {
            Mockito.`when`(assetLoader.getFileDataById(idRaw)).thenReturn(weathersLocal)
        }
        weatherLocalDataSource = WeatherLocalDataSourceImpl(assetLoader,idRaw,scope)
        weatherLocalDataSourceError = WeatherLocalDataSourceImpl(assetLoaderError,idRaw2,scopeError)
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getAll() = scope.runTest {
        val result = mutableListOf<WeatherDataLocal>()
        async {
            weatherLocalDataSource.getAll().collect { result.addAll(it.weathers) }
        }.await()
        assert( result.size > 4)
    }
    @Test
    fun getAllWithFailure() = scopeError.runTest {
        val result = mutableListOf<WeatherDataLocal>()
         async {
            weatherLocalDataSourceError.getAll()
                .collect { it?.weathers?.let { it1 -> result.addAll(it1) } }
        }.await()
        assert( result.size == 0)
    }


    @Test
    fun getWeatherByIndex() = scope.runTest {
        val index = 2
        val result = weatherLocalDataSource.getWeatherByIndex(index)
        assertEquals(weathersLocal.weathers[index], result)
    }

    @Test
    fun getWeatherByIndexFailure() = scopeError.runTest {
        val index = 2
        var pass = false
        try {
            coroutineScope {
                async {
                    weatherLocalDataSourceError.getWeatherByIndex(index)
                }.await()
            }
        } catch (e: Exception) {
            pass = true
        }

        assert(pass)
    }
    @Test
    fun getWeatherByIndexWithIndexMoreGreater() = scope.runTest {
        val index =  Random.nextInt(1000)
        val result = weatherLocalDataSource.getWeatherByIndex(index)
        val newIndex = index%(weathersLocal.weathers.size)
        assertEquals(weathersLocal.weathers[newIndex], result)
    }



    @Test
    fun getSize() = scope.runTest {
        val size = weatherLocalDataSource.getSize()
        assertEquals(5, size)
    }
}