package uz.gita.maxwayclone.presentation.profile.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.domain.usecase.auth.DeleteAccountUseCase
import uz.gita.maxwayclone.domain.usecase.auth.GetUserInfoUseCase
import uz.gita.maxwayclone.domain.usecase.auth.UpdateUserUseCase

class EditProfileViewModelFactory(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditProfileViewModel(deleteAccountUseCase, getUserInfoUseCase, updateUserUseCase) as T
    }
}