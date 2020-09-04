package co.cdmunoz.nasaroverphotos.di

import co.cdmunoz.nasaroverphotos.data.api.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun testNetworkModule(baseUrl: String) = module {
    single { provideTestRetrofit(baseUrl) }
    single { provideTestApiService(get(), ApiService::class.java) }
}

fun provideTestRetrofit(baseUrl: String): Retrofit =
    Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()

fun provideTestApiService(retrofit: Retrofit, apiService: Class<ApiService>) =
    createService(retrofit, apiService)