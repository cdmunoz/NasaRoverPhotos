package co.cdmunoz.nasaroverphotos.ui.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.cdmunoz.nasaroverphotos.MainActivity
import co.cdmunoz.nasaroverphotos.data.model.Photo
import co.cdmunoz.nasaroverphotos.databinding.FragmentPhotoDetailsBinding
import co.cdmunoz.nasaroverphotos.databinding.FragmentPhotoDetailsBindingImpl
import co.cdmunoz.nasaroverphotos.utils.extensions.setImageFromUrl
import co.cdmunoz.nasaroverphotos.utils.extensions.setImageFromUrlWithProgressBar
import co.cdmunoz.nasaroverphotos.utils.extensions.zoomImageFromThumb

class PhotoDetailsFragment : Fragment() {

    companion object {
        private val TAG = PhotoDetailsFragment::class.java.name
        const val PHOTO_ARG = "PHOTO_ARG"
    }

    private lateinit var binding: FragmentPhotoDetailsBinding
    private lateinit var photo: Photo
    private var shortAnimationDuration: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadArguments()
    }

    private fun loadArguments() {
        arguments?.getParcelable<Photo>(PHOTO_ARG)?.let {
            photo = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentPhotoDetailsBindingImpl.inflate(inflater)
        binding.photoBinding = photo
        binding.lifecycleOwner = this@PhotoDetailsFragment
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Retrieve and cache the system's default "short" animation time.
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        binding.photoDetailsImageZoom.setImageFromUrl(photo.imgSrc)
        with(binding.photoDetailsImage) {
            setImageFromUrlWithProgressBar(photo.imgSrc, binding.photoDetailsProgress)
            setOnClickListener {
                zoomImageFromThumb(binding.photoDetailsImageZoom,
                    binding.photoDetailsContainer,
                    shortAnimationDuration)
            }
        }
    }

    override fun onResume() {
        (activity as MainActivity).supportActionBar?.show()
        super.onResume()
    }
}
