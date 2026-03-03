package uz.gita.maxwayclone.presentation.profile.register_phone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.data.sources.remote.api.AuthApi
import uz.gita.maxwayclone.domain.repository.impl.AuthRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.RegisterUseCaseImpl

class PhoneViewModelFactory(
    private val api: AuthApi
) : ViewModelProvider.Factory {

    val repository = AuthRepositoryImpl(api = api)
    private val registerUseCase = RegisterUseCaseImpl(repository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PhoneViewModel(registerUseCase) as T
    }
}