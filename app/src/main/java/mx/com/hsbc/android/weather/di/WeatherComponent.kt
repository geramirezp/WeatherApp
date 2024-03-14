package mx.com.hsbc.android.weather.di

import dagger.Subcomponent
import mx.com.hsbc.android.weather.presentation.weather.WeatherActivity

@WeatherScope
@Subcomponent(modules = [WeatherModule::class,WeatherDataModule::class])
interface WeatherComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WeatherComponent
    }
    fun inject(weatherActivity : WeatherActivity)
}
