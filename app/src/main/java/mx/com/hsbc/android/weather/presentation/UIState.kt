package mx.com.hsbc.android.weather.presentation

sealed interface UIState {
    data class Success<T >(val data: T) : UIState
    data class Error(val message: String) : UIState
    data object InProgress : UIState
}
