package mx.com.hsbc.android.weather.data.datasource.local

import android.content.Context
import android.content.res.Resources
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import mx.com.hsbc.android.weather.data.datasource.AssetLoader
import mx.com.hsbc.android.weather.data.datasource.local.entity.WeatherDataLocal
import mx.com.hsbc.android.weather.data.datasource.local.entity.WeathersLocal
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.InputStream

class AssetLoaderLocalTest {
    private lateinit var assetLoaderLocal : AssetLoader
    private val id = 1
    val jsonText = """{ "temperature":{
        "data": [
        {"place": "King's Park","value": 25,"unit": "C" },
        {"place": "Hong Kong Observatory","value":26,"unit":"C"},
        {"place":"Wong Chuk Hang", "value": 26,"unit": "C" },
        {"place":"Ta Kwu Ling", "value": 26,"unit": "C"}],
        "recordTime": "2023-11-09T17:00:00+08:00" }}
    """
    private lateinit var resources: Resources
    private lateinit var inputStream: InputStream
    private lateinit var context: Context

    @Before
    fun setup() {
        resources = mock(Resources::class.java)
        context = mock(Context::class.java)
        inputStream = jsonText.byteInputStream()
        `when`(context.resources).thenReturn(resources)
        `when`(resources.openRawResource(id)).thenReturn(inputStream)
       assetLoaderLocal = AssetLoaderLocal(resources)
    }
    @Test
    fun testGetFileDataById() = runTest {
        val result = assetLoaderLocal.getFileDataById(id)
        Assert.assertEquals(4, result.weathers.size)
    }
}
