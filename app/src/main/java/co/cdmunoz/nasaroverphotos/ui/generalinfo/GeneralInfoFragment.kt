package co.cdmunoz.nasaroverphotos.ui.generalinfo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.cdmunoz.nasaroverphotos.MainActivity
import co.cdmunoz.nasaroverphotos.R
import co.cdmunoz.nasaroverphotos.databinding.FragmentGeneralInfoBinding
import co.cdmunoz.nasaroverphotos.utils.extensions.setImageFromResourcesWithProgressBar

class GeneralInfoFragment : Fragment() {

    private lateinit var binding: FragmentGeneralInfoBinding

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentGeneralInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindings()
    }

    private fun initBindings() {
        //hides activity's toolbar
        (activity as MainActivity).supportActionBar?.hide()
        with(binding.toolbar) {
            this.setTitle(R.string.title_info)
        }
        binding.generalInfoToolbarImage.setImageFromResourcesWithProgressBar(R.drawable.mars_exploration_rover,
            binding.generalInfoProgress)
    }

    override fun onPause() {
        (activity as MainActivity).supportActionBar?.show()
        super.onPause()
    }
}
