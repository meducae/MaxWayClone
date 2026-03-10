package uz.gita.maxwayclone.presentation.exceptions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.Fragment404exceptionBinding

class Fragment404Exception : Fragment(R.layout.fragment_404exception) {
    private val binding by viewBinding(Fragment404exceptionBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAgain.setOnClickListener {
            findNavController().navigate(R.id.action_fragment404Exception_to_nav_home)
        }
    }
}