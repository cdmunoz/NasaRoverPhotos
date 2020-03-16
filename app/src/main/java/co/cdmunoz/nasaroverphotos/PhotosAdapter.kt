package co.cdmunoz.nasaroverphotos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import co.cdmunoz.nasaroverphotos.databinding.RowPhotoHomeBinding

class PhotosAdapter(private val photosList: List<String>) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            RowPhotoHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = photosList.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(photosList[position])
    }

    class PhotosViewHolder(rowBinding: RowPhotoHomeBinding) :
        RecyclerView.ViewHolder(rowBinding.root) {
        private val binding = rowBinding

        fun bind(str: String) {
            binding.camera = str
            val bundle = bundleOf("PHOTO_NAME" to str)
            binding.root.setOnClickListener { view ->
                Navigation.findNavController(view).navigate(R.id.action_home_to_details, bundle)
            }
        }
    }
}