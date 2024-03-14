package mx.com.hsbc.android.weather.data.datasource.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import mx.com.hsbc.android.weather.R
import org.junit.After

@RunWith(AndroidJUnit4::class)
class WeatherLocalDataSourceImplInstrumentedTest {

    private lateinit var weatherLocalDataSource : WeatherLocalDataSource
    private val scope = TestScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        val assetLoaderLocal = AssetLoaderLocal(appContext.resources)
        weatherLocalDataSource = WeatherLocalDataSourceImpl(assetLoaderLocal,R.raw.data,scope)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun testGetSize() = scope.runTest {
        val size = weatherLocalDataSource.getSize()
        assert(size == 26)
    }



}