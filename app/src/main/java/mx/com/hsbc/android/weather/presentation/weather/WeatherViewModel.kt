package mx.com.hsbc.android.weather.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.com.hsbc.android.weather.domain.FetchResult
import mx.com.hsbc.android.weather.domain.GetRandomWeatherUseCase
import mx.com.hsbc.android.weather.presentation.TypeField
import mx.com.hsbc.android.weather.presentation.UIEvenType
import mx.com.hsbc.android.weather.presentation.UIState
import mx.com.hsbc.android.weather.presentation.weather.model.WeatherModel
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val getRandomWeatherUseCase: GetRandomWeatherUseCase) : ViewModel() {
    private var _state = MutableStateFlow<UIState>(UIState.InProgress)
    val state = _state.asStateFlow()

    init {
        requestNewWeather()
    }

    fun onEvent(eventType: UIEvenType) {
        when (eventType) {
            UIEvenType.NextWeather -> requestNewWeather()
            is UIEvenType.OnChangeField -> {
                if (_state.value is UIState.Success<*>) {
                    val data = (_state.value as UIState.Success<*>).data
                    if (data is WeatherModel) {
                        when (eventType.typeField) {
                            is TypeField.Location -> _state.update {
                                UIState.Success(
                                    WeatherModel(
                                        eventType.value,
                                        data.temperature
                                    )
                                )
                            }

                            is TypeField.Temperature -> _state.update {
                                UIState.Success(
                                    WeatherModel(
                                        data.place,
                                        eventType.value
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun requestNewWeather() {
        viewModelScope.launch {
            _state.update { UIState.InProgress }
            when (val result = getRandomWeatherUseCase()) {
                is FetchResult.Success -> {
                    val weather = result.data.toMap()
                    _state.update { UIState.Success(weather) }
                    print(weather.place)
                    println(weather.temperature)
                }

                is FetchResult.Error -> {
                    _state.update { UIState.Error(result.error.message ?: "Error") }
                    println(result.error.message)
                }
            }
        }
    }
}
