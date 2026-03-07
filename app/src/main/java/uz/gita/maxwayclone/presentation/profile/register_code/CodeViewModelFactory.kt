package uz.gita.maxwayclone.presentation.profile.register_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.data.sources.remote.api.AuthApi
import uz.gita.maxwayclone.domain.repository.impl.AuthRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.RepeatUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.VerifyUseCaseImpl

class CodeViewModelFactory(
    private val api: AuthApi
) : ViewModelProvider.Factory {

    private val repository = AuthRepositoryImpl(api = api)
    private val verifyUseCase = VerifyUseCaseImpl(repository = repository)
    private val repeatUseCase = RepeatUseCaseImpl(repository = repository)
    private val tokenManager = TokenManager.getInstance()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CodeViewModel(verifyUseCase, repeatUseCase, tokenManager) as T
    }
}