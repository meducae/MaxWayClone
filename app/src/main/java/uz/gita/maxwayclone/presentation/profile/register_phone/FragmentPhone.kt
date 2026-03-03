package uz.gita.maxwayclone.presentation.profile.register_phone

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.RegisterUiState
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.data.sources.remote.request.register.RegisterRequest
import uz.gita.maxwayclone.databinding.FragmentRegisterPhoneBinding

class FragmentPhone : Fragment(R.layout.fragment_register_phone) {

    private val binding by viewBinding(FragmentRegisterPhoneBinding::bind)

    private val viewModel: PhoneViewModel by viewModels { PhoneViewModelFactory(ApiClient.authApi) }

    private val tokenManager = TokenManager.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeRegister()

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(
                R.id.fragmentName, null,
                androidx.navigation.NavOptions.Builder()
                    .setPopUpTo(R.id.nav_host, true)
                    .build()
            )
        }

        binding.editTextRegisterPhone.doAfterTextChanged { editable ->
            val number = editable?.toString()?.trim().orEmpty()
            val valid = viewModel.isValidPhone(number)

            binding.buttonContinue.isEnabled = valid

            val colorRes = if (valid) R.color.white else R.color.gray
            binding.buttonContinue.setTextColor(ContextCompat.getColor(requireContext(), colorRes))
        }


        binding.buttonContinue.setOnClickListener {
            val number = binding.editTextRegisterPhone.text.toString().trim()
            val phone = "+998" + binding.editTextRegisterPhone.text.toString().trim()

            if (!viewModel.isValidPhone(number)) {
                Toast.makeText(requireContext(), "номер не подходит", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (number.isBlank()) {
                Toast.makeText(requireContext(), "Phone is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(RegisterRequest(phone = phone))
            tokenManager.savePhone(phone)
        }
    }

    private fun observeRegister() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.state.collect { state ->
                    when(state){

                        is RegisterUiState.Default -> {
                            binding.progressBar.isVisible = false
                        }

                        is RegisterUiState.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        is RegisterUiState.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                        }
                        is RegisterUiState.Success -> {
                            findNavController().navigate(R.id.action_fragmentPhone_to_fragmentCode)
                            Toast.makeText(requireContext(), "Код: ${state.success.data.code}", Toast.LENGTH_SHORT).show()
                            viewModel.reset()
                        }

                    }
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()

        val bottomNav =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.isVisible = false
    }


}