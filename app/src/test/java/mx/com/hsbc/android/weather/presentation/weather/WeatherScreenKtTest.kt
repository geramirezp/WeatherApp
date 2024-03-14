package mx.com.hsbc.android.weather.presentation.weather

import androidx.activity.ComponentActivity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import mx.com.hsbc.android.weather.di.DaggerApplicationComponent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
//@Config(application = HiltTestApplication::class)
@LooperMode(LooperMode.Mode.PAUSED)
class WeatherScreenKtTest {

    @get:Rule
    val composeTestRule =  createAndroidComposeRule<ComponentActivity>()


    @Test
    fun weatherScreen() {
    }

    @Test
    fun errorComponent() {
        composeTestRule.setContent {
            ErrorComponent(message = "Error message", retry = {})
        }
        composeTestRule.onNodeWithText("Error message").assertIsDisplayed()
        composeTestRule.onNodeWithText( "Try again").assertIsDisplayed()
    }

    @Test
    fun loadingComponent() {
        composeTestRule.setContent {
            LoadingComponen()
        }
        composeTestRule.onNodeWithTag("loading").assertIsDisplayed()
    }

    @Test
    fun formWeatherComponent() {
        composeTestRule.setContent {
            FormWeatherComponent(nextWeather = {}, onEvent = {})
        }
        composeTestRule.onNodeWithText( "Next Random Location").assertIsDisplayed()
    }
}