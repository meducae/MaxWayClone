package uz.gita.maxwayclone.presentation.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.data.sources.remote.response.user.ResponseUserInfo
import uz.gita.maxwayclone.domain.usecase.auth.GetUserInfoUseCase


class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    sealed class Event {
        data class GetInfoSuccess(val data: ResponseUserInfo) : Event()
        data class Error(val message: String) : Event()
    }

    private val _events = MutableSharedFlow<Event>(replay = 0)

    val events = _events.asSharedFlow()

    fun getUserInfo(token: String) {
        if (token.isBlank()) {
            viewModelScope.launch { _events.emit(Event.Error("token not found")) }
            return
        }

        viewModelScope.launch {
            getUserInfoUseCase(token).collect { result ->
                result.onSuccess { _events.emit(Event.GetInfoSuccess(it)) }
                result.onFailure { _events.emit(Event.Error(it.message ?: "Error")) }
            }
        }
    }
}