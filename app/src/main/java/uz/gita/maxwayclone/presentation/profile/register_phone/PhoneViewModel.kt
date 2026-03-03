package uz.gita.maxwayclone.presentation.profile.register_phone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.RegisterUiState
import uz.gita.maxwayclone.data.sources.remote.request.register.RegisterRequest
import uz.gita.maxwayclone.domain.usecase.auth.RegisterUseCase

class PhoneViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {


    private val _state = MutableStateFlow<RegisterUiState>(RegisterUiState.Default)
    val state = _state.asStateFlow()

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            _state.value = RegisterUiState.Loading
            try {
                registerUseCase(request).collect { result ->
                    result.onSuccess { data ->
                        _state.value = RegisterUiState.Success(data)
                    }.onFailure { e ->
                        _state.value = RegisterUiState.Error(e.message ?: "Xatolik")
                    }
                }
            } catch (e: Exception) {
                _state.value = RegisterUiState.Error(e.message ?: "Xatolik")
            }
        }
    }

    fun isValidPhone(phone: String): Boolean {
        return phone.length == 9 && phone.all { it.isDigit() }
    }

    fun reset(){
        _state.value = RegisterUiState.Default
    }

}