package uz.gita.maxwayclone.presentation.profile.register_phone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.maxwayclone.data.sources.remote.request.register.RegisterRequest
import uz.gita.maxwayclone.data.sources.remote.response.register.RegisterResponse
import uz.gita.maxwayclone.domain.usecase.auth.RegisterUseCase

class FragmentViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    private val _events = MutableSharedFlow<RegisterEvent>()
    val events = _events.asSharedFlow()

    sealed class RegisterEvent {
        data class Success(val response: RegisterResponse) : RegisterEvent()
        data class Error(val message: String) : RegisterEvent()
    }

    fun register(request: RegisterRequest) {
        registerUseCase(request)
            .onEach { result ->
                result.onSuccess { _events.emit(RegisterEvent.Success(it)) }
                result.onFailure { _events.emit(RegisterEvent.Error(it.message ?: "Error")) }
            }
            .launchIn(viewModelScope)
    }

    fun isValidPhone(phone: String): Boolean {
        return phone.length == 9 && phone.all { it.isDigit() }
    }

}