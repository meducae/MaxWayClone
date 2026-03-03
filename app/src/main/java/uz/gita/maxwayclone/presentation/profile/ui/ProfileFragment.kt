package uz.gita.maxwayclone.presentation.profile.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.GetUserUiState
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.databinding.FragmentProfileBinding
import uz.gita.maxwayclone.presentation.dialogs.DeleteConfirmDialog
import uz.gita.maxwayclone.presentation.dialogs.EditProfileBottomSheetDialog

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels { ProfileViewModelFactory(ApiClient.authApi) }
    private val tokenManager = TokenManager.getInstance()
    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()

        binding.buttonEnterAccount.setOnClickListener {
            findNavController().navigate(R.id.action_nav_profile_to_fragmentName)
        }

        binding.buttonEditAccount.setOnClickListener {
            EditProfileBottomSheetDialog({
                binding.userName.text = tokenManager.getName()
                binding.userPhone.text = tokenManager.getPhone()
            }).show(parentFragmentManager, "edit_profile")
        }

        binding.buttonLogout.setOnClickListener {
            DeleteConfirmDialog().show(parentFragmentManager, "logout_dialog")
        }

        observeEvents()

    }

    override fun onResume() {
        super.onResume()
        setup()
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.isVisible = true

        val token = tokenManager.getToken()
        if (token.isNotBlank()) {
            viewModel.getUserInfo(token)
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect{ state ->
                    when (state) {

                        is GetUserUiState.Default -> {
                            binding.progressBar.isVisible = false
                        }
                        is GetUserUiState.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        is GetUserUiState.Success -> {
                            val name = state.success.data.name ?: ""
                            binding.progressBar.isVisible = false

                            if (name.isNotEmpty()){
                                binding.userName.text = name
                                tokenManager.saveName(name)
                            }else{
                                binding.userName.text = tokenManager.getName()
                            }
                        }
                        is GetUserUiState.Error -> {
                            Toast.makeText(requireContext(), state.error , Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }
    }
    private fun setup() {
        binding.userName.text = tokenManager.getName()
        binding.userPhone.text = tokenManager.getPhone()

        val token = tokenManager.getToken()
        if (token.isNotBlank()) {
            binding.panelAccount.visibility = View.INVISIBLE
            binding.editAccount.visibility = View.VISIBLE
            binding.buttonLogout.visibility = View.VISIBLE
        } else {
            binding.panelAccount.visibility = View.VISIBLE
            binding.editAccount.visibility = View.INVISIBLE
            binding.buttonLogout.visibility = View.INVISIBLE
        }
    }
}