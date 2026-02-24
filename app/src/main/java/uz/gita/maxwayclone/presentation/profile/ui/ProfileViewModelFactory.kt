package uz.gita.maxwayclone.presentation.profile.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.domain.usecase.auth.GetUserInfoUseCase

class ProfileViewModelFactory(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(getUserInfoUseCase) as T
    }
}