package uz.gita.maxwayclone.presentation.dialogs

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.EditProfileUiState
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.databinding.DialogExitBinding
import uz.gita.maxwayclone.presentation.profile.edit_profile.EditProfileViewModel
import uz.gita.maxwayclone.presentation.profile.edit_profile.EditProfileViewModelFactory


class DeleteConfirmDialog : DialogFragment(R.layout.dialog_exit) {

    private val binding by viewBinding(DialogExitBinding::bind)

    private val viewModel: EditProfileViewModel by viewModels {
        EditProfileViewModelFactory(ApiClient.authApi)
    }

    private val tokenManager = TokenManager.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exit.setOnClickListener {
            val token = tokenManager.getToken()
            viewModel.deleteAccount(token)
        }

        binding.stay.setOnClickListener {
            dismiss()
        }

        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.state.collect { state ->
                    when (state) {

                        EditProfileUiState.Default -> {
                        }

                        EditProfileUiState.Loading -> {
                        }

                        is EditProfileUiState.DeleteSuccess -> {
                            tokenManager.clear()

                            val bundle = Bundle().apply { putBoolean("DELETED", true) }

                            findNavController().navigate(
                                R.id.nav_profile,
                                bundle,
                                androidx.navigation.NavOptions.Builder()
                                    .setPopUpTo(R.id.nav_host, true)
                                    .build()
                            )

                            viewModel.reset()
                            dismiss()
                        }

                        is EditProfileUiState.Error -> {
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                            viewModel.reset()
                        }

                        // Bu dialogga kerak emas holatlar:
                        is EditProfileUiState.GetInfoSuccess,
                        is EditProfileUiState.UpdateSuccess -> Unit
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}