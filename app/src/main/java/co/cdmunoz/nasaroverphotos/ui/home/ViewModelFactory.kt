package co.cdmunoz.nasaroverphotos.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.cdmunoz.nasaroverphotos.data.api.ApiService
import co.cdmunoz.nasaroverphotos.data.repository.PhotosRepository

class ViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotosViewModel::class.java)) {
            return PhotosViewModel(
                PhotosRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}