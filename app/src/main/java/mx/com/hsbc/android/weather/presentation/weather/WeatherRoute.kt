package mx.com.hsbc.android.weather.presentation.weather

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.com.hsbc.android.weather.presentation.UIEvenType.NextWeather

@Composable
fun WeatherRoute(getVmFactory: () -> ViewModelProvider.Factory) {
    val viewModel = viewModel<WeatherViewModel>(factory = getVmFactory())
    println("route")
    WeatherScreen(viewModel.state, { viewModel.onEvent(NextWeather) }, viewModel::onEvent)
}
