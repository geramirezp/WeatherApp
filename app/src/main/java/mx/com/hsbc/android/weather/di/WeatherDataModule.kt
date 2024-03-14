package mx.com.hsbc.android.weather.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import mx.com.hsbc.android.weather.data.WeatherRepositoryImpl
import mx.com.hsbc.android.weather.data.datasource.AssetLoader
import mx.com.hsbc.android.weather.data.datasource.local.AssetLoaderLocal
import mx.com.hsbc.android.weather.data.datasource.local.WeatherLocalDataSource
import mx.com.hsbc.android.weather.data.datasource.local.WeatherLocalDataSourceImpl
import mx.com.hsbc.android.weather.data.datasource.local.entity.WeathersLocal
import mx.com.hsbc.android.weather.domain.repository.WeatherRepository
import mx.com.hsbc.android.weather.presentation.weather.WeatherViewModel

@Module
abstract class WeatherDataModule {
    @Binds
    @WeatherScope
    abstract fun providesDataSourceLocal(weatherLocalDataSourceImpl: WeatherLocalDataSourceImpl): WeatherLocalDataSource

    @Binds
    @WeatherScope
    abstract fun providesAssetLoader(assetLoaderLocal: AssetLoaderLocal): AssetLoader

    @Binds
    @WeatherScope
    abstract fun providesRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @IntoMap
    @WeatherScope
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindWeatherViewModel(vm: WeatherViewModel): ViewModel

    @Binds
    @WeatherScope
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
