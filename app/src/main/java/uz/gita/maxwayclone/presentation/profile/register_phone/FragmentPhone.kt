package uz.gita.maxwayclone.presentation.profile.register_phone

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
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
import com.vicmikhailau.maskededittext.MaskedFormatter
import com.vicmikhailau.maskededittext.MaskedWatcher
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.RegisterUiState
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.data.sources.remote.request.register.RegisterRequest
import uz.gita.maxwayclone.databinding.FragmentRegisterPhoneBinding

class FragmentPhone : Fragment(R.layout.fragment_register_phone) {

    private var oldStatusBarColor = 0
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

        val formattedNumber = binding.editTextRegisterPhone
        val formatter = MaskedFormatter("(##) ### ## ##")

        binding.editTextRegisterPhone.addTextChangedListener(MaskedWatcher(formatter, formattedNumber))


        binding.buttonContinue.setOnClickListener {
            val fullText = binding.editTextRegisterPhone.text.toString()

            val unmaskedString = fullText.replace(Regex("\\D"), "")

            val phone = "+998$unmaskedString"


            if (unmaskedString.length != 9) {
                Toast.makeText(requireContext(), "Неправильный номер", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (unmaskedString.isBlank()) {
                Toast.makeText(requireContext(), "Неправильный номер", Toast.LENGTH_SHORT).show()
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
                    when (state) {

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
                            Toast.makeText(
                                requireContext(),
                                "Код: ${state.success.data.code}",
                                Toast.LENGTH_SHORT
                            ).show()
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

        oldStatusBarColor = requireActivity().window.statusBarColor
        requireActivity().window.statusBarColor = Color.WHITE
    }

    override fun onPause() {
        super.onPause()

        requireActivity().window.statusBarColor = oldStatusBarColor
    }


}