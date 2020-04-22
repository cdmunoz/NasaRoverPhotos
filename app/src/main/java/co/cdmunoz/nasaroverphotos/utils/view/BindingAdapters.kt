package co.cdmunoz.nasaroverphotos.utils.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import co.cdmunoz.nasaroverphotos.utils.extensions.setImageFromUrl

class BindingAdapters {
    companion object {
        @BindingAdapter("android:image_url")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, auctionImageUrl: String) {
            imageView.setImageFromUrl(auctionImageUrl)
        }
    }
}