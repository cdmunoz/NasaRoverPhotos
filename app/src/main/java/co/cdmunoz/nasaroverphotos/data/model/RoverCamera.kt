package co.cdmunoz.nasaroverphotos.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoverCamera(@SerializedName("full_name") val fullName: String, @SerializedName("name") val name: String) :
    Parcelable