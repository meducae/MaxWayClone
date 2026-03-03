package uz.gita.maxwayclone.presentation.profile.register_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.domain.usecase.auth.RepeatUseCase
import uz.gita.maxwayclone.domain.usecase.auth.VerifyUseCase

class CodeViewModelFactory(
    private val verifyUseCase: VerifyUseCase,
    private val repeatUseCase: RepeatUseCase,
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CodeViewModel(verifyUseCase, repeatUseCase, tokenManager) as T
    }
}