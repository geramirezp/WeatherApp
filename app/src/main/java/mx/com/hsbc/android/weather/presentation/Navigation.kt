package mx.com.hsbc.android.weather.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.com.hsbc.android.weather.presentation.weather.WeatherRoute

object Destinations {
    const val WEATHER_ROUTE = "weather"
}
@Composable
fun WeatherNavHost(
    navController: NavHostController = rememberNavController(),
    getVmFactory: () -> ViewModelProvider.Factory
) {
    NavHost(navController = navController, startDestination = Destinations.WEATHER_ROUTE) {
        composable(Destinations.WEATHER_ROUTE) {
            WeatherRoute(getVmFactory)
        }
    }
}
