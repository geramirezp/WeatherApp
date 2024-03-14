package mx.com.hsbc.android.weather.data.datasource.local.entity

import com.google.gson.Gson
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TemperatureTest {

    val jsonText = """{ "temperature":{
        "data": [
        {"place": "King's Park","value": 25,"unit": "C" },
        {"place": "Hong Kong Observatory","value":26,"unit":"C"},
        {"place":"Wong Chuk Hang", "value": 26,"unit": "C" },
        {"place":"Ta Kwu Ling", "value": 26,"unit": "C"}],
        "recordTime": "2023-11-09T17:00:00+08:00" }}
    """

    val jsonTextFail = """{ "temperatur":{
        "data": [
        {"place": "King's Park","value": 25,"unit": "C" },
        {"place": "Hong Kong Observatory","value":26,"unit":"C"},
        {"place":"Wong Chuk Hang", "value": 26,"unit": "C" },
        {"place":"Ta Kwu Ling", "value": 26,"unit": "C"}],
        "recordTime": "2023-11-09T17:00:00+08:00" }}
    """
    val jsonTextFail3 = """ "temperatur":{
        
        "data": [
        {"place": "King's Park","value": 25,"unit": "C" },
        {"place": "Hong Kong Observatory","value":26,"unit":"C"},
        {"place":"Wong Chuk Hang", "value": 26,"unit": "C" },
        {"place":"Ta Kwu Ling", "value": 26,"unit": "C"}],
        "recordTime": "2023-11-09T17:00:00+08:00" }}
    """

    private lateinit var gson : Gson

    @Before
    fun setup() {
        gson = Gson()
    }

    @Test
    fun testGetTemperatureFromStringJson() {
        val result = gson.fromJson(jsonText, Temperature::class.java)
        assert(result is Temperature)
        assert(result.temperature.weathers.size > 2)
    }
    @Test
    fun testGetTemperatureFromStringJsonBadFormatTemperature() {
        val result : Temperature = gson.fromJson(jsonTextFail, Temperature::class.java)
        assert(result.temperature == null)
    }
    @Test
    fun testGetTemperatureFromStringJsonBadFormat() {
        val result  = try{ gson.fromJson(jsonTextFail3, Temperature::class.java) } catch (e: Exception) { e}
        assert(result is Exception)
    }
}