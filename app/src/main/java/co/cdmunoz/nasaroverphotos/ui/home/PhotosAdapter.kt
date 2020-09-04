package co.cdmunoz.nasaroverphotos.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.cdmunoz.nasaroverphotos.R
import co.cdmunoz.nasaroverphotos.data.model.Photo
import co.cdmunoz.nasaroverphotos.databinding.RowPhotoHomeBinding
import co.cdmunoz.nasaroverphotos.ui.details.PhotoDetailsFragment
import co.cdmunoz.nasaroverphotos.utils.extensions.setImageFromUrlWithProgressBar


class PhotosAdapter(private val photosList: ArrayList<Photo>) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowPhotoHomeBinding.inflate(inflater, parent, false)
        return PhotosViewHolder(binding)
    }

    override fun getItemCount() = photosList.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(photosList[position])
    }

    class PhotosViewHolder(rowBinding: RowPhotoHomeBinding) :
        RecyclerView.ViewHolder(rowBinding.root) {
        private val binding = rowBinding

        fun bind(photo: Photo) {
            binding.photoBinding = photo
            binding.executePendingBindings()
            binding.rowPhotoRoverImg.setImageFromUrlWithProgressBar(photo.getSecureImgSrc(),
                binding.rowPhotoRoverProgress)
            binding.root.setOnClickListener { view ->
                val bundle = bundleOf(PhotoDetailsFragment.PHOTO_ARG to photo)
                view.findNavController().navigate(R.id.action_home_to_details, bundle)
            }
        }
    }
}