package mx.com.hsbc.android.weather.di

import dagger.Module
import dagger.Provides
import mx.com.hsbc.android.weather.R

@Module
class WeatherModule {

    @Provides
    @WeatherScope
    fun providesIdJson() : Int = R.raw.data
}
