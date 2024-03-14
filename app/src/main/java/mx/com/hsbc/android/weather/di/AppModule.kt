package mx.com.hsbc.android.weather.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module( subcomponents = [WeatherComponent::class])
class AppModule {

    @Provides
    @Singleton
    fun providesResource(context: Context) : Resources = context.resources

    @Provides
    @Singleton
    fun providesDispatcher () : CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun providesCoroutineScopeApplication (dispatcher: CoroutineDispatcher) : CoroutineScope =  CoroutineScope(
        SupervisorJob() + dispatcher)
}
