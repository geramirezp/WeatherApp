package mx.com.hsbc.android.weather.presentation.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import mx.com.hsbc.android.weather.WeatherApplication
import mx.com.hsbc.android.weather.presentation.WeatherNavHost
import mx.com.hsbc.android.weather.presentation.theme.WeatherTheme
import javax.inject.Inject

class WeatherActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as WeatherApplication).applicationComponent.weatherComponent().create().inject(this)
        setContent {
            WeatherTheme {
                WeatherNavHost { viewModelFactory }
            }
        }
    }
}
