package mx.com.hsbc.android.weather.presentation.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import mx.com.hsbc.android.weather.domain.FetchResult
import mx.com.hsbc.android.weather.domain.GetRandomWeatherUseCase
import mx.com.hsbc.android.weather.domain.entity.Weather
import mx.com.hsbc.android.weather.presentation.TypeField
import mx.com.hsbc.android.weather.presentation.UIEvenType
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import mx.com.hsbc.android.weather.presentation.UIState
import mx.com.hsbc.android.weather.presentation.weather.model.WeatherModel

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var usecase: GetRandomWeatherUseCase
    private lateinit var viewModel: WeatherViewModel
    private lateinit var testCoroutineScheduler: TestCoroutineScheduler
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope

    @Before
    fun setUp() {
        testCoroutineScheduler = TestCoroutineScheduler()
        testDispatcher = StandardTestDispatcher(testCoroutineScheduler)
        testScope = TestScope(testDispatcher)
        Dispatchers.setMain(testDispatcher)
        usecase = mock(GetRandomWeatherUseCase::class.java)
        viewModel = spy(WeatherViewModel(usecase))
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel() // Limpiar el dispatcher de prueba
    }

    @Test
    fun requestNewRandomWeather()= testScope.runTest {
        val mockWeather = Weather("Test", 22, "C")
        `when`(usecase.invoke()).thenReturn(FetchResult.Success(mockWeather))
        viewModel.onEvent(UIEvenType.NextWeather)
        val currentState = viewModel.state.first()
        assert(currentState is UIState.InProgress)
        testScope.advanceUntilIdle()
        val finalState = viewModel.state.first()
        assert(finalState is UIState.Success<*> )
    }


    @Test
    fun stateError() = testScope.runTest {
        `when`(usecase.invoke()).thenReturn(FetchResult.Error(Exception("Something was wrong")))
        viewModel.onEvent(UIEvenType.NextWeather)
        testScope.advanceUntilIdle()
        val currentState = viewModel.state.first()
        assert(currentState is UIState.Error)
    }

    @Test
    fun onEventInputLocation() = testScope.runTest {
        val mockWeather = Weather("Test", 27, "C")
        val location = "London"
        `when`(usecase.invoke()).thenReturn(FetchResult.Success(mockWeather))
        viewModel.onEvent(UIEvenType.NextWeather)
        testScope.advanceUntilIdle()
        val middleState = viewModel.state.first()
        assert(middleState is UIState.Success<*> )
        viewModel.onEvent(UIEvenType.OnChangeField(TypeField.Location, location))
        testScope.advanceUntilIdle()
        val finalState = viewModel.state.first()
        assert(finalState is UIState.Success<*> )
        val newData = (finalState as UIState.Success<WeatherModel> ).data
        assertEquals(location, newData.place)
    }
    @Test
    fun onEventInputTemperature() = testScope.runTest {
        val mockWeather = Weather("Test", 27, "C")
        val temperature = "25 C"
        `when`(usecase.invoke()).thenReturn(FetchResult.Success(mockWeather))
        viewModel.onEvent(UIEvenType.NextWeather)
        testScope.advanceUntilIdle()
        val middleState = viewModel.state.first()
        assert(middleState is UIState.Success<*> )
        viewModel.onEvent(UIEvenType.OnChangeField(TypeField.Temperature, temperature))
        testScope.advanceUntilIdle()
        val finalState = viewModel.state.first()
        assert(finalState is UIState.Success<*> )
        val newData = (finalState as UIState.Success<WeatherModel> ).data
        assertEquals(temperature, newData.temperature)
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        val mockWeather = Weather("Test", 22, "C")
        `when`(usecase.invoke()).thenReturn(FetchResult.Success(mockWeather))
        assertEquals(UIState.InProgress, viewModel.state.value)
    }
}