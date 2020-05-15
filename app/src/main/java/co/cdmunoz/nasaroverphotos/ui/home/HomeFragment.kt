package co.cdmunoz.nasaroverphotos.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.cdmunoz.nasaroverphotos.MainActivity
import co.cdmunoz.nasaroverphotos.R
import co.cdmunoz.nasaroverphotos.data.api.ApiService
import co.cdmunoz.nasaroverphotos.data.api.RetrofitService
import co.cdmunoz.nasaroverphotos.data.model.Photo
import co.cdmunoz.nasaroverphotos.databinding.FragmentHomeBinding
import co.cdmunoz.nasaroverphotos.databinding.FragmentHomeBindingImpl
import co.cdmunoz.nasaroverphotos.utils.Result
import co.cdmunoz.nasaroverphotos.utils.list.InfiniteScrollListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    companion object {
        private val TAG = HomeFragment::class.java.name
    }

    private lateinit var binding: FragmentHomeBinding
    private var photosViewModel: PhotosViewModel? = null

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBindingImpl.inflate(inflater)
        binding.lifecycleOwner = this@HomeFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindings()
        initViewModels()
        initObservers()
    }

    private fun initBindings() {
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.homePhotosList.run {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
    }

    private fun initViewModels() {
        if (null == photosViewModel) {
            photosViewModel = ViewModelProviders.of(this,
                ViewModelFactory(RetrofitService.createService(ApiService::class.java)))
                .get(PhotosViewModel::class.java)
            photosViewModel?.loadData()
        }
    }

    private fun initObservers() {
        photosViewModel?.getPhotos()?.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    renderList(result.data)
                    binding.homePhotosProgressContainer.visibility = View.GONE
                    binding.homePhotosList.visibility = View.VISIBLE
                }
                is Result.InProgress -> {
                    binding.homePhotosProgressContainer.visibility = View.VISIBLE
                    binding.homePhotosList.visibility = View.GONE
                }
                is Result.Error -> {
                    binding.homePhotosProgressContainer.visibility = View.GONE
                    Toast.makeText(activity, result.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun fetchMoreData() {
        photosViewModel?.loadDataNextPage()
    }

    private fun renderList(photos: ArrayList<Photo>) {
        if (photos.isNotEmpty()) {
            //when screen starts
            if (photosViewModel?.getCurrentPage() == 1 || binding.homePhotosList.adapter?.itemCount == 0) {
                setRecyclerData(photos)
            } else { //when load more
                if (binding.homePhotosList.adapter == null) { //after load more
                    setRecyclerData(photos)
                    binding.homePhotosList.adapter?.notifyDataSetChanged()
                }
            }
            //load state of rv
            if (photosViewModel?.listState != null) {
                binding.homePhotosList.layoutManager?.onRestoreInstanceState(photosViewModel?.listState)
                photosViewModel?.listState = null
            }
        } else {
            showSnackBarMessage()
        }
    }

    private fun setRecyclerData(photos: ArrayList<Photo>) {
        with(binding.homePhotosList) {
            adapter = PhotosAdapter(photos)
            addOnScrollListener(InfiniteScrollListener({ fetchMoreData() },
                layoutManager as LinearLayoutManager))
        }
    }

    private fun showSnackBarMessage() {
        val bottomNavView: BottomNavigationView = activity?.findViewById(R.id.bottom_nav)!!
        Snackbar.make(bottomNavView, R.string.no_data_msg, Snackbar.LENGTH_SHORT).apply {
            anchorView = bottomNavView
        }.show()
    }

    override fun onResume() {
        (activity as MainActivity).supportActionBar?.show()
        super.onResume()
    }

    override fun onDestroyView() {
        photosViewModel?.listState = binding.homePhotosList.layoutManager?.onSaveInstanceState()
        super.onDestroyView()
    }
}
