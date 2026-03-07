package uz.gita.maxwayclone.presentation.profile.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.EditProfileUiState
import uz.gita.maxwayclone.data.sources.remote.request.register.UpdateRequest
import uz.gita.maxwayclone.domain.usecase.auth.DeleteAccountUseCase
import uz.gita.maxwayclone.domain.usecase.auth.GetUserInfoUseCase
import uz.gita.maxwayclone.domain.usecase.auth.UpdateUserUseCase

class EditProfileViewModel(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EditProfileUiState>(EditProfileUiState.Default)
    val state = _state.asStateFlow()

    fun reset() {
        _state.value = EditProfileUiState.Default
    }

    fun deleteAccount(token: String) {
        if (token.isBlank()) {
            _state.value = EditProfileUiState.Error("Регистрация не найдена")
            return
        }

        viewModelScope.launch {
            _state.value = EditProfileUiState.Loading

            try {
                deleteAccountUseCase(token).collect { result ->
                    result.onSuccess { response ->
                        _state.value = EditProfileUiState.DeleteSuccess(response)
                    }.onFailure { e ->
                        _state.value = EditProfileUiState.Error("не удалось удалить")
                    }
                }
            } catch (e: Exception) {
                _state.value = EditProfileUiState.Error("не удалось удалить")
            }
        }
    }

    fun getUserInfo(token: String) {
        if (token.isBlank()) {
            _state.value = EditProfileUiState.Error("Пользователь не найдена")
            return
        }

        viewModelScope.launch {
            _state.value = EditProfileUiState.Loading

            try {
                getUserInfoUseCase(token).collect { result ->
                    result.onSuccess { response ->
                        _state.value = EditProfileUiState.GetInfoSuccess(response)
                    }.onFailure { e ->
                        _state.value = EditProfileUiState.Error("не подключен к интернету")
                    }
                }
            } catch (e: Exception) {
                _state.value = EditProfileUiState.Error("не подключен к интернету")
            }
        }
    }

    fun updateUser(token: String, request: UpdateRequest) {
        if (token.isBlank()) {
            _state.value = EditProfileUiState.Error("токен не найдено")
            return
        }

        viewModelScope.launch {
            _state.value = EditProfileUiState.Loading

            try {
                updateUserUseCase(token, request).collect { result ->
                    result.onSuccess { response ->
                        _state.value = EditProfileUiState.UpdateSuccess(response)
                    }.onFailure {
                        _state.value = EditProfileUiState.Error("Обновление не удалось.")
                    }
                }
            } catch (e: Exception) {
                _state.value = EditProfileUiState.Error("Обновление не удалось.")
            }
        }
    }
}