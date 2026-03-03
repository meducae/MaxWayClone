package uz.gita.maxwayclone.presentation.profile.register_phone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.domain.usecase.auth.RegisterUseCase

class FragmentViewModelFactory(
    private val registerUseCase: RegisterUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FragmentViewModel(registerUseCase) as T
    }
}