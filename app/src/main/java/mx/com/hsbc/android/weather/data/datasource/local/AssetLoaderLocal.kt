package mx.com.hsbc.android.weather.data.datasource.local

import android.content.res.Resources
import com.google.gson.Gson
import mx.com.hsbc.android.weather.data.datasource.AssetLoader
import mx.com.hsbc.android.weather.data.datasource.local.entity.Temperature
import mx.com.hsbc.android.weather.data.datasource.local.entity.WeathersLocal
import javax.inject.Inject

class AssetLoaderLocal @Inject constructor(private val resources: Resources, ) : AssetLoader {
    override suspend fun getFileDataById(id: Int): WeathersLocal {
        val json = resources.openRawResource(id).use { it.bufferedReader().use { reader -> reader.readText() } }
        return Gson().fromJson(json, Temperature::class.java).temperature
    }
}