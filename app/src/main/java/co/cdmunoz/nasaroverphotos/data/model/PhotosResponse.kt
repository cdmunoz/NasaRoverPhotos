package co.cdmunoz.nasaroverphotos.data.model


import com.google.gson.annotations.SerializedName

data class PhotosResponse(@SerializedName("photos") val photos: ArrayList<Photo>)