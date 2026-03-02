package uz.gita.maxwayclone.presentation.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.GetUserUiState
import uz.gita.maxwayclone.domain.usecase.auth.GetUserInfoUseCase


class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {


    private val _state = MutableStateFlow<GetUserUiState>(GetUserUiState.Default)
    val state = _state.asStateFlow()

    fun getUserInfo(token: String) {
        if (token.isBlank()) {
            _state.value = GetUserUiState.Error("Токен не найдено")
            return
        }

        viewModelScope.launch {
            _state.value = GetUserUiState.Loading

            try {
                getUserInfoUseCase(token).collect { result ->
                    result.onSuccess { data ->
                        _state.value = GetUserUiState.Success(data)
                    }.onFailure { e ->
                        _state.value = GetUserUiState.Error(e.message ?: "Xatolik")
                    }
                }
            } catch (e: Exception) {
                _state.value = GetUserUiState.Error(e.message ?: "Xatolik")
            }
        }
    }
}