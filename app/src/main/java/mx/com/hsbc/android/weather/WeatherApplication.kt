package mx.com.hsbc.android.weather

import android.app.Application
import mx.com.hsbc.android.weather.di.ApplicationComponent
import mx.com.hsbc.android.weather.di.DaggerApplicationComponent

class WeatherApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        buildComponent()
    }

    private fun buildComponent() {
        applicationComponent = DaggerApplicationComponent.builder().context(this).build()
    }
}
