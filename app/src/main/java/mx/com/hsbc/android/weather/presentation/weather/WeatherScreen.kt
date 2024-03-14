package mx.com.hsbc.android.weather.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import mx.com.hsbc.android.weather.R
import mx.com.hsbc.android.weather.presentation.TypeField
import mx.com.hsbc.android.weather.presentation.UIEvenType
import mx.com.hsbc.android.weather.presentation.UIState
import mx.com.hsbc.android.weather.presentation.weather.model.WeatherModel

@Preview
@Composable
fun WeatherScreen(state: StateFlow<UIState>, nextWeather: () -> Unit, onEvent: (UIEvenType) -> Unit) {
    val uiState by state.collectAsStateWithLifecycle()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when (uiState) {
            is UIState.Success<*> -> {
                println("Success")
                val data = (uiState as UIState.Success<*>).data
                if (data is WeatherModel) {
                    FormWeatherComponent(data, nextWeather, onEvent)
                }
            }

            is UIState.InProgress -> {
                println("INprogres")
                LoadingComponen()
            }

            is UIState.Error -> {
                println("Error")
                ErrorComponent((uiState as UIState.Error).message) { nextWeather() }
            }
        }
    }
}

@Composable
fun ErrorComponent(message: String, retry: () -> Unit) {
    Box {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.error),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(.5f)
            )
            Text(message, style = MaterialTheme.typography.titleLarge)
        }
        Button(onClick = retry, modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(16.dp)) {
            Text(stringResource(R.string.label_button_try))
        }
    }
}

@Composable
fun LoadingComponen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(.2f).align(Alignment.Center).testTag("loading"),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
    }
}

@Composable
fun FormWeatherComponent(
    weather: WeatherModel = WeatherModel("", ""),
    nextWeather: () -> Unit,
    onEvent: (UIEvenType) -> Unit
) {
    var size by remember { mutableStateOf(Size.Zero) }
    val density = LocalDensity.current
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center).fillMaxWidth().padding(16.dp).onGloballyPositioned {
            size = it.parentLayoutCoordinates?.size?.toSize() ?: Size.Zero
        }) {
            Row {
                Text(
                    stringResource(R.string.label_location),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(with(density) { size.width.toDp() * .3f })
                        .align(Alignment.CenterVertically)
                )
                OutlinedTextField(
                    textStyle = MaterialTheme.typography.titleMedium,
                    value = weather.place,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { onEvent(UIEvenType.OnChangeField(TypeField.Location, it)) },
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    stringResource(R.string.label_temperature),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(with(density) { size.width.toDp() * .3f })
                        .align(Alignment.CenterVertically)
                )
                OutlinedTextField(
                    textStyle = MaterialTheme.typography.titleMedium,
                    value = weather.temperature,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    onValueChange = {
                        onEvent(UIEvenType.OnChangeField(TypeField.Temperature, it))
                    },
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = nextWeather, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.label_button_next))
            }
        }
    }
}
