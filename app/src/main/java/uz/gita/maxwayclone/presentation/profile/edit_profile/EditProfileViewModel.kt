package uz.gita.maxwayclone.presentation.profile.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.data.sources.remote.request.register.UpdateRequest
import uz.gita.maxwayclone.data.sources.remote.response.delete_account.ResponseDeleteAccount
import uz.gita.maxwayclone.data.sources.remote.response.update.ResponseUpdate
import uz.gita.maxwayclone.data.sources.remote.response.user.ResponseUserInfo
import uz.gita.maxwayclone.domain.usecase.auth.DeleteAccountUseCase
import uz.gita.maxwayclone.domain.usecase.auth.GetUserInfoUseCase
import uz.gita.maxwayclone.domain.usecase.auth.UpdateUserUseCase

class EditProfileViewModel(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    sealed class Event {
        data class DeleteSuccess(val data: ResponseDeleteAccount) : Event()
        data class GetInfoSuccess(val data: ResponseUserInfo) : Event()
        data class UpdateSuccess(val data: ResponseUpdate) : Event()


        data class Error(val message: String) : Event()

    }

    private val _events = MutableSharedFlow<Event>(replay = 0)
    val events = _events.asSharedFlow()

    fun deleteAccount(token: String) {
        if (token.isBlank()) {
            viewModelScope.launch { _events.emit(Event.Error("Регистрация не найдена")) }
            return
        }

        viewModelScope.launch {
            deleteAccountUseCase(token).collect { result ->
                result.onSuccess { response ->
                    _events.emit(Event.DeleteSuccess(response))
                }
                result.onFailure { e ->
                    _events.emit(Event.Error("${e.message}"))
                }
            }
        }
    }

    fun getUserInfo(token: String){

        if (token.isBlank()){
            viewModelScope.launch { _events.emit(Event.Error("Пользователь не найдена")) }
            return
        }

        viewModelScope.launch {
            getUserInfoUseCase(token = token).collect { result ->
                result.onSuccess{ response ->
                    _events.emit(Event.GetInfoSuccess(response))
                }
                result.onFailure { exception ->
                    _events.emit(Event.Error("${exception.message}"))
                }
            }
        }
    }

    fun updateUser(token: String, request: UpdateRequest) {
        if (token.isBlank()) {
            viewModelScope.launch { _events.emit(Event.Error("токен не найдено")) }
            return
        }

        viewModelScope.launch {
            updateUserUseCase(token, request).collect { result ->
                result.onSuccess { response ->
                    _events.emit(Event.UpdateSuccess(response))
                }
                result.onFailure { e ->
                    _events.emit(Event.Error(e.message ?: "Update error"))
                }
            }
        }
    }
}