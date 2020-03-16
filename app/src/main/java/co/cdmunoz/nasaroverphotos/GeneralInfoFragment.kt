package co.cdmunoz.nasaroverphotos


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.cdmunoz.nasaroverphotos.databinding.FragmentGeneralInfoBinding
import co.cdmunoz.nasaroverphotos.databinding.FragmentGeneralInfoBindingImpl

class GeneralInfoFragment : Fragment() {

    private lateinit var binding: FragmentGeneralInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeneralInfoBindingImpl.inflate(inflater)
        return binding.root
    }
}
