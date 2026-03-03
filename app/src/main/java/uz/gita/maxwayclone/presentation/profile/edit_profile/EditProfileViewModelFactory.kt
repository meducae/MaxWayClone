package uz.gita.maxwayclone.presentation.profile.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.data.sources.remote.api.AuthApi
import uz.gita.maxwayclone.domain.repository.impl.AuthRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.DeleteAccountUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.GetUserInfoUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.UpdateUserUseCaseImpl

class EditProfileViewModelFactory(
    private val api: AuthApi
) : ViewModelProvider.Factory {

    private val repository = AuthRepositoryImpl(api = api)
    private val deleteAccountUseCase = DeleteAccountUseCaseImpl(repository = repository)
    private val getUserInfoUseCase = GetUserInfoUseCaseImpl(repository = repository)
    private val updateUserUseCase = UpdateUserUseCaseImpl(repository = repository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditProfileViewModel(deleteAccountUseCase, getUserInfoUseCase, updateUserUseCase) as T
    }
}