package co.cdmunoz.nasaroverphotos

import android.app.Application
import co.cdmunoz.nasaroverphotos.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class NasaRoverPhotosApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
        startKoin {
            androidContext(this@NasaRoverPhotosApp)
            modules(appModules)
        }
    }
}