package co.cdmunoz.nasaroverphotos.utils.view

import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class PhotosRequestListener(private val progress: View) : RequestListener<Drawable> {
    override fun onLoadFailed(e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean): Boolean {
        progress.visibility = View.GONE
        return false
    }

    override fun onResourceReady(resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean): Boolean {
        progress.visibility = View.GONE
        return false
    }
}