package co.cdmunoz.nasaroverphotos.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import co.cdmunoz.nasaroverphotos.utils.view.GlideApp
import co.cdmunoz.nasaroverphotos.utils.view.PhotosRequestListener

fun ImageView.setImageFromUrl(imageUrl: String) {
    GlideApp.with(context).load(imageUrl).thumbnail(0.1f).into(this)
}

fun ImageView.setImageFromUrlWithProgressBar(url: String, progress: View) {
    GlideApp.with(this.context).load(url).thumbnail(0.1f).listener(PhotosRequestListener(progress))
        .into(this)
}

fun ImageView.setImageFromResourcesWithProgressBar(resourceId: Int, progress: View) {
    GlideApp.with(this.context).load(resourceId).thumbnail(0.1f)
        .listener(PhotosRequestListener(progress)).into(this)
}

//From https://developer.android.com/training/animation/zoom#kotlin and adapted to current scenario
fun ImageView.zoomImageFromThumb(zoomedImg: View,
    zoomToContainer: View,
    shortAnimationDuration: Int) {
    var currentAnimator: Animator? = null
    currentAnimator?.cancel()
    // Calculate the starting and ending bounds for the zoomed-in image.
    val startBoundsInt = Rect()
    val finalBoundsInt = Rect()
    val globalOffset = Point()
    // The start bounds are the global visible rectangle and set the container view's
    // offset as the origin for the bounds
    this.getGlobalVisibleRect(startBoundsInt)
    zoomToContainer.getGlobalVisibleRect(finalBoundsInt, globalOffset)
    startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
    finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

    val startBounds = RectF(startBoundsInt)
    val finalBounds = RectF(finalBoundsInt)

    // Adjust the start bounds keeping same aspect ratio
    val startScale: Float
    if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
        // Extend start bounds horizontally
        startScale = startBounds.height() / finalBounds.height()
        val startWidth: Float = startScale * finalBounds.width()
        val deltaWidth: Float = (startWidth - startBounds.width()) / 2
        startBounds.left -= deltaWidth.toInt()
        startBounds.right += deltaWidth.toInt()
    } else {
        // Extend start bounds vertically
        startScale = startBounds.width() / finalBounds.width()
        val startHeight: Float = startScale * finalBounds.height()
        val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
        startBounds.top -= deltaHeight.toInt()
        startBounds.bottom += deltaHeight.toInt()
    }

    // Hide the other views in container
    for (index in 0 until (zoomToContainer as ViewGroup).childCount) {
        val nextChild = zoomToContainer.getChildAt(index)
        nextChild.alpha = 0f
    }
    zoomedImg.apply {
        visibility = View.VISIBLE
        alpha = 1f
    }

    zoomedImg.pivotX = 0f
    zoomedImg.pivotY = 0f

    // Construct and run the parallel animation of the four translation and
    // scale properties (X, Y, SCALE_X, and SCALE_Y).
    currentAnimator = AnimatorSet().apply {
        play(ObjectAnimator.ofFloat(zoomedImg, View.X, startBounds.left, finalBounds.left)).apply {
            with(ObjectAnimator.ofFloat(zoomedImg, View.Y, startBounds.top, finalBounds.top))
            with(ObjectAnimator.ofFloat(zoomedImg, View.SCALE_X, startScale, 1f))
            with(ObjectAnimator.ofFloat(zoomedImg, View.SCALE_Y, startScale, 1f))
        }
        duration = shortAnimationDuration.toLong()
        interpolator = DecelerateInterpolator()
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                currentAnimator = null
            }

            override fun onAnimationCancel(animation: Animator) {
                currentAnimator = null
            }
        })
        start()
    }

    // Zoom back down to the original bounds and show all hidden views
    zoomedImg.setOnClickListener {
        currentAnimator?.cancel()
        currentAnimator = AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(zoomedImg, View.X, startBounds.left)).apply {
                with(ObjectAnimator.ofFloat(zoomedImg, View.Y, startBounds.top))
                with(ObjectAnimator.ofFloat(zoomedImg, View.SCALE_X, startScale))
                with(ObjectAnimator.ofFloat(zoomedImg, View.SCALE_Y, startScale))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    for (index in 0 until (zoomToContainer as ViewGroup).childCount) {
                        val nextChild = zoomToContainer.getChildAt(index)
                        nextChild.alpha = 1f
                    }
                    zoomedImg.apply {
                        visibility = View.GONE
                        alpha = 0f
                    }
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    for (index in 0 until (zoomToContainer as ViewGroup).childCount) {
                        val nextChild = zoomToContainer.getChildAt(index)
                        nextChild.alpha = 1f
                    }
                    zoomedImg.apply {
                        visibility = View.GONE
                        alpha = 0f
                    }
                    currentAnimator = null
                }
            })
            start()
        }
    }
}