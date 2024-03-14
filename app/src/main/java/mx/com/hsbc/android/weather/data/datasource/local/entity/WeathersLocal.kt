package mx.com.hsbc.android.weather.data.datasource.local.entity

import com.google.gson.annotations.SerializedName


data class WeathersLocal(
    @SerializedName("data")
    val weathers: List<WeatherDataLocal>,
    val recordTime: String
)
