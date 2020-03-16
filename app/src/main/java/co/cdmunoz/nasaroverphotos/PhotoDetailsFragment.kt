package co.cdmunoz.nasaroverphotos


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.cdmunoz.nasaroverphotos.databinding.FragmentPhotoDetailsBinding
import co.cdmunoz.nasaroverphotos.databinding.FragmentPhotoDetailsBindingImpl

class PhotoDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPhotoDetailsBinding
    private lateinit var cameraStr: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadArguments()
    }

    private fun loadArguments() {
        arguments?.getString("PHOTO_NAME")?.let {
            cameraStr = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoDetailsBindingImpl.inflate(inflater)
        binding.camera = cameraStr
        return binding.root
    }
}
