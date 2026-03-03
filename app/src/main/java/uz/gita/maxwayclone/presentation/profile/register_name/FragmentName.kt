package uz.gita.maxwayclone.presentation.profile.register_name

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.databinding.FragmentRegisterNameBinding

class FragmentName: Fragment(R.layout.fragment_register_name) {

    private val tokenManager = TokenManager.getInstance()
    private val binding by viewBinding(FragmentRegisterNameBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            tokenManager.getName()
            findNavController().navigate(R.id.nav_profile, null,
                androidx.navigation.NavOptions.Builder()
                    .setPopUpTo(R.id.nav_host, true)
                    .build()
            )
        }

        binding.panelEditText.setText(tokenManager.getName())

        binding.buttonContinue.setOnClickListener {

            val name = binding.panelEditText.text.toString().trim()

            if (name.isEmpty()) return@setOnClickListener

            tokenManager.saveName(name = name)

            findNavController().navigate(R.id.action_fragmentName_to_fragmentPhone)
        }

    }

    override fun onResume() {
        super.onResume()

        val bottomNav =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.isVisible = false
    }

}