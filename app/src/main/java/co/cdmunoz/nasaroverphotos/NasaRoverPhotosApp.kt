package co.cdmunoz.nasaroverphotos

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree


class NasaRoverPhotosApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}