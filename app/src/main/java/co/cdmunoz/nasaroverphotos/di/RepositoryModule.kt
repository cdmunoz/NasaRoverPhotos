package co.cdmunoz.nasaroverphotos.di

import co.cdmunoz.nasaroverphotos.data.repository.PhotosRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        PhotosRepository(get())
    }
}