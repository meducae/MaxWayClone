package uz.gita.maxwayclone.presentation.profile.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.data.sources.remote.api.AuthApi
import uz.gita.maxwayclone.domain.repository.impl.AuthRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.GetUserInfoUseCaseImpl

class ProfileViewModelFactory(
    private val api: AuthApi
) : ViewModelProvider.Factory {
    private val repository = AuthRepositoryImpl(api = api)
    private val getUserInfoUseCase = GetUserInfoUseCaseImpl(repository = repository)


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(getUserInfoUseCase) as T
    }
}