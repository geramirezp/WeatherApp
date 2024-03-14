package mx.com.hsbc.android.weather.data.datasource

import mx.com.hsbc.android.weather.data.datasource.local.entity.WeathersLocal

interface AssetLoader {
    suspend fun getFileDataById(id : Int) : WeathersLocal
}
