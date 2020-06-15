package co.cdmunoz.nasaroverphotos.data.repository

import co.cdmunoz.nasaroverphotos.BuildConfig
import co.cdmunoz.nasaroverphotos.data.api.ApiService
import co.cdmunoz.nasaroverphotos.data.model.Photo
import co.cdmunoz.nasaroverphotos.utils.Result
import retrofit2.HttpException
import timber.log.Timber

class PhotosRepository(private val apiService: ApiService) : BaseRepository() {

    companion object {
        private val TAG = PhotosRepository::class.java.name
        const val GENERAL_ERROR_CODE = 499
    }

    private var apiKey: String = BuildConfig.API_KEY

    suspend fun getPhotosFromApi(sol: Int, page: Int): Result<ArrayList<Photo>> {
        var result: Result<ArrayList<Photo>> = handleSuccess(arrayListOf())
        try {
            val response = apiService.getPhotos(sol, apiKey, page)
            response?.let {
                it.body()?.photos?.let { photosResponse ->
                    result = handleSuccess(photosResponse)
                }
                it.errorBody()?.let { responseErrorBody ->
                    if (responseErrorBody is HttpException) {
                        responseErrorBody.response()?.code()?.let { errorCode ->
                            result = handleException(errorCode)
                        }
                    } else result = handleException(GENERAL_ERROR_CODE)
                }
            }
        } catch (error: HttpException) {
            Timber.e("$TAG - Error: ${error.message}")
            return handleException(error.code())
        }
        return result
    }

}