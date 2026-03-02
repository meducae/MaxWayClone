package uz.gita.maxwayclone.presentation.profile.register_code

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
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.RepeatUiState
import uz.gita.maxwayclone.VerifyUiState
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.databinding.FragmentRegisterCodeBinding

class FragmentCode : Fragment(R.layout.fragment_register_code) {

    private val tokenManager = TokenManager.getInstance()
    private val binding by viewBinding(FragmentRegisterCodeBinding::bind)

    private val viewModel: CodeViewModel by viewModels { CodeViewModelFactory(ApiClient.authApi) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val phone = tokenManager.getPhone()
        binding.phoneNumber.text = phone


        viewModel.startTimer()

        observe()

        setupOtpValidation()

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.fragmentPhone, null,
                androidx.navigation.NavOptions.Builder()
                    .setPopUpTo(R.id.nav_host, true)
                    .build()
            )
        }

        binding.btnContinue.setOnClickListener {
            val otp = getOtpString()

            viewModel.verify(phone, otp.toInt())
        }

        binding.textTime.setOnClickListener {
            viewModel.repeat(phone)
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.stateVerify.collect { state ->
                        when (state) {

                            is VerifyUiState.Default -> {
                                binding.progressBar.isVisible = false
                            }
                            is VerifyUiState.Loading -> {
                                binding.progressBar.isVisible = true
                            }
                            is VerifyUiState.Success -> {
                                findNavController().navigate(R.id.action_fragmentCode_to_nav_profile2, null,
                                    androidx.navigation.NavOptions.Builder()
                                        .setPopUpTo(R.id.nav_host, true)
                                        .build()
                                )
                            }
                            else -> {
                                setOtpError()
                                Toast.makeText(requireContext(), "Xatolik", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                launch {
                    viewModel.stateRepeat.collect { state ->
                        when(state){
                            is RepeatUiState.Default -> {
                                binding.progressBar.isVisible = false
                            }
                            is RepeatUiState.Success -> {
                                Toast.makeText(requireContext(), "Новий код отправили", Toast.LENGTH_SHORT).show()
                                Toast.makeText(requireContext(), "Новий код: ${state.success.data.code}", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(requireContext(), "Xatolik", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                launch {
                    viewModel.timeLeft.collect { sec ->
                        binding.time.text = formatTime(sec)
                    }
                }
                launch {
                    viewModel.canRepeat.collect { can ->
                        binding.textTime.isEnabled = can
                        binding.textTime.alpha = if (can) 1f else 0.5f
                    }
                }
            }
        }
    }

    private fun getOtpString(): String {
        val n1 = binding.number1.text.toString().trim()
        val n2 = binding.number2.text.toString().trim()
        val n3 = binding.number3.text.toString().trim()
        val n4 = binding.number4.text.toString().trim()

        return (n1 + n2 + n3 + n4).trim()
    }

    private fun setOtpError() {
        listOf(binding.number1, binding.number2, binding.number3, binding.number4)
            .forEach { it.setBackgroundResource(R.drawable.background_otp_error) }
    }

    private fun formatTime(sec: Int): String {
        val m = sec / 60
        val s = sec % 60
        return String.format("%02d:%02d", m, s)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).isVisible = false
    }

    private fun setupOtpValidation() {

        val fields = listOf(
            binding.number1,
            binding.number2,
            binding.number3,
            binding.number4
        )

        fields.forEach { editText ->
            editText.doAfterTextChanged {

                val isFilled = fields.all { it.text.toString().trim().length == 1 }

                binding.btnContinue.isEnabled = isFilled

                val colorRes = if (isFilled) R.color.white else R.color.gray

                binding.btnContinue.setTextColor(
                    ContextCompat.getColor(requireContext(), colorRes)
                )
            }
        }
    }
}