package mx.com.hsbc.android.weather.domain

sealed class FetchResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : FetchResult<T>()
    data class Error(val error: Throwable) : FetchResult<Nothing>()
}
