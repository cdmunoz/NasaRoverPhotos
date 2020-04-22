package co.cdmunoz.nasaroverphotos.data.repository

import android.util.Log
import co.cdmunoz.nasaroverphotos.BuildConfig
import co.cdmunoz.nasaroverphotos.data.api.ApiService
import co.cdmunoz.nasaroverphotos.data.model.PhotosResponse
import retrofit2.Response

class PhotosRepository(val apiService: ApiService) {

    private val TAG = PhotosRepository::class.java.name

    var apiKey: String = BuildConfig.API_KEY

    suspend fun getPhotosFromApi(sol: Int, page: Int): Response<PhotosResponse>? {
        try {
            val response = apiService.getPhotos(sol, apiKey, page)
            response?.let {
                return it
            }
        } catch (error: Exception) {
            Log.e(TAG, "Error: ${error.message}")
            return null
        }
        return null
    }

}