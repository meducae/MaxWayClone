package uz.gita.maxwayclone.presentation.dialogs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.data.sources.remote.request.register.UpdateRequest
import uz.gita.maxwayclone.databinding.BottomSheetDialogBinding
import uz.gita.maxwayclone.domain.sevise_locator.ServiceLocator
import uz.gita.maxwayclone.presentation.profile.edit_profile.EditProfileViewModel

class EditProfileBottomSheetDialog(
    private val onSuccess: () -> Unit
) : BottomSheetDialogFragment(R.layout.bottom_sheet_dialog) {

    private val viewModel: EditProfileViewModel by viewModels { ServiceLocator.editProfileVmFactory }
    private val binding by viewBinding(BottomSheetDialogBinding::bind)
    private val tokenManager = TokenManager.getInstance()

    private val token by lazy { tokenManager.getToken() }

    private var alreadyFilled = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserInfo(token)

        binding.birthday.setText(tokenManager.getBirthday())
        binding.userName.setText(tokenManager.getName())

        binding.dataPiker.setOnClickListener {
            DialogDataPicker { d, m, y ->
                val birth = "%02d.%02d.%04d".format(d, m, y)
                binding.birthday.setText(birth)
                tokenManager.saveBirthday(birth)
            }.show(parentFragmentManager, "birth_date")
        }

        binding.buttonSaveEdit.setOnClickListener {

            val newName = binding.userName.text.toString().trim()
            val birthDate = binding.birthday.text.toString().trim()

            if (newName.isBlank()) {
                binding.userName.error = "Имя пусто"
                return@setOnClickListener
            }
            if (birthDate.isBlank()) {
                binding.birthday.error = "Дата не найдено"
                return@setOnClickListener
            }

            val request = UpdateRequest(
                name = newName,
                birthDate = birthDate
            )

            viewModel.updateUser(token, request)
        }

        binding.buttonDeleteEdit.setOnClickListener {
            viewModel.deleteAccount(token)
        }

        observeEvents()
    }

    override fun onResume() {
        super.onResume()
        binding.birthday.setText(tokenManager.getBirthday())
        binding.userName.setText(tokenManager.getName())
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    when (event) {

                        is EditProfileViewModel.Event.GetInfoSuccess -> {
                            if (alreadyFilled) return@collect
                            alreadyFilled = true

                            val info = event.data
                            val name = info.data.name ?: ""
                            if (name.isBlank()){
                                binding.birthday.setText(tokenManager.getBirthday())
                                binding.userName.setText(tokenManager.getName())
                            }else{
                                binding.userName.setText(info.data.name)
                                binding.birthday.setText(info.data.birthDate)
                            }

                        }

                        is EditProfileViewModel.Event.UpdateSuccess -> {
                            Toast.makeText(requireContext(), "Обновлено", Toast.LENGTH_SHORT).show()

                            tokenManager.saveName(binding.userName.text.toString().trim())
                            onSuccess.invoke()
                            dismiss()
                        }

                        is EditProfileViewModel.Event.DeleteSuccess -> {
                            tokenManager.clear()

                            val bundle = Bundle().apply { putBoolean("DELETED", true) }

                            findNavController().navigate(
                                R.id.nav_profile,
                                bundle,
                                androidx.navigation.NavOptions.Builder()
                                    .setPopUpTo(R.id.nav_host, true)
                                    .build()
                            )
                            dismiss()
                        }

                        is EditProfileViewModel.Event.Error -> {
                            Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}