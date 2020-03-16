package co.cdmunoz.nasaroverphotos


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.cdmunoz.nasaroverphotos.databinding.FragmentHomeBinding
import co.cdmunoz.nasaroverphotos.databinding.FragmentHomeBindingImpl

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBindingImpl.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindings()
    }

    private fun initBindings() {
        val photosAdapter = PhotosAdapter(getPhotosList())
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.homePhotosList.apply {
            adapter = photosAdapter
            layoutManager = linearLayoutManager

        }
    }

    private fun getPhotosList(): List<String> {
        val photosList = mutableListOf<String>()
        for (i in 0..25) {
            photosList.add("Photo $i")
        }
        return photosList
    }
}
