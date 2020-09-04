package co.cdmunoz.nasaroverphotos.di

import co.cdmunoz.nasaroverphotos.ui.home.PhotosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PhotosViewModel(get()) }
}