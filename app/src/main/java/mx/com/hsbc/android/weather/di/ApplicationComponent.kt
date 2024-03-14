package mx.com.hsbc.android.weather.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [AppModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance fun context ( context : Context) : Builder
        fun build() : ApplicationComponent
    }

    fun weatherComponent() : WeatherComponent.Factory
}
