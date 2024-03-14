package mx.com.hsbc.android.weather.presentation.weather

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class WeatherScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun weatherScreen() {
    }

    @Test
    fun errorComponent() {
        composeTestRule.setContent {
            ErrorComponent(message = "Error message", retry = {})
        }
        composeTestRule.onNodeWithText("Error message").assertIsDisplayed()
        composeTestRule.onNodeWithText("Try again").assertIsDisplayed()
    }

    }