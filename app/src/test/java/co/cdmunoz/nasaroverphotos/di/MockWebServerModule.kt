package co.cdmunoz.nasaroverphotos.di

import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module

val mockWebServerModule = module {
    factory { MockWebServer() }
}