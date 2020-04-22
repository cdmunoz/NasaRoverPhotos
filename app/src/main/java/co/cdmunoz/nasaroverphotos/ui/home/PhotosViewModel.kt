package co.cdmunoz.nasaroverphotos.ui.home

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.cdmunoz.nasaroverphotos.data.model.Photo
import co.cdmunoz.nasaroverphotos.data.repository.PhotosRepository
import co.cdmunoz.nasaroverphotos.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotosViewModel(private val photosRepository: PhotosRepository) : ViewModel() {

    /*private val photos = MutableLiveData<Resource<ArrayList<Photo>>>()*/
    private val photos = MutableLiveData<Result<ArrayList<Photo>>>()
    private var currentPage = 1
    var listState: Parcelable? = null //to save and restore rv's adapter

    fun getPhotos() = photos

    fun getCurrentPage() = currentPage

    fun loadData() {
        try {
            if (currentPage == 1) {
                /*photos.postValue(Resource.loading(null))*/
                photos.postValue(Result.InProgress)
            }
            viewModelScope.launch(Dispatchers.IO) {
                val response = photosRepository.getPhotosFromApi(1000, currentPage)
                response?.let {
                    val photosList = it.body()?.photos
                    photosList?.let { list ->
                        if (currentPage == 1) { //set photos for first page
                            /*photos.postValue(Resource.success(list))*/
                            photos.postValue(Result.Success(list))
                        } else { //add photos to current list
                            /*val currentPhotos: ArrayList<Photo>? = photos.value?.data*/
                            val currentPhotos: ArrayList<Photo>? = photos.value?.extractData
                            if (currentPhotos == null || currentPhotos.isEmpty()) {
                                /*photos.postValue(Resource.success(list))*/
                                photos.postValue(Result.Success(list))
                            } else {
                                currentPhotos.addAll(list)
                                /*photos.postValue(Resource.success(currentPhotos))*/
                                photos.postValue(Result.Success(currentPhotos))
                            }
                        }
                    } ?: run {
                        /*photos.postValue(Resource.success(arrayListOf()))*/
                        photos.postValue(Result.Success(arrayListOf()))
                    }
                } ?: run {
                    /*photos.postValue(Resource.success(arrayListOf()))*/
                    photos.postValue(Result.Success(arrayListOf()))
                }
            }
        } catch (error: Exception) {
            /*photos.postValue(Resource.error("Something went wrong: ${error.message}", null))*/
            photos.postValue(Result.Error(error))
        }
    }

    fun loadDataNextPage() {
        currentPage++
        loadData()
    }
}