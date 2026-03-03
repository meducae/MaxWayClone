package uz.gita.maxwayclone.presentation.profile.register_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.data.sources.remote.request.register.RepeatRequest
import uz.gita.maxwayclone.data.sources.remote.request.register.VerifyRequest
import uz.gita.maxwayclone.domain.usecase.auth.RepeatUseCase
import uz.gita.maxwayclone.domain.usecase.auth.VerifyUseCase

class CodeViewModel(
    private val verifyUseCase: VerifyUseCase,
    private val repeatUseCase: RepeatUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    sealed class Event {
        object VerifySuccess : Event()
        object RepeatSuccess : Event()
        data class Error(val message: String) : Event()
    }

    private val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    private val _timeLeft = MutableStateFlow(60)
    val timeLeft = _timeLeft.asStateFlow()

    private val _canRepeat = MutableStateFlow(false)
    val canRepeat = _canRepeat.asStateFlow()

    private var timerJob: Job? = null

    fun startTimer(seconds: Int = 60) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            _canRepeat.value = false
            _timeLeft.value = seconds

            while (_timeLeft.value > 0) {
                delay(1000)
                _timeLeft.value = _timeLeft.value - 1
            }

            _canRepeat.value = true
        }
    }

    fun verify(phone: String, code: Int) {

        viewModelScope.launch {
            verifyUseCase(VerifyRequest(phone = phone, code = code))
                .collect { result ->
                    result.onSuccess {
                        _events.emit(Event.VerifySuccess)
                        tokenManager.saveToken(it.data.token)
                    }
                    result.onFailure { e ->
                        _events.emit(Event.Error(e.message ?: "Kod xato"))
                    }
                }
        }
    }

    fun repeat(phone: String) {
        if (!_canRepeat.value) return

        viewModelScope.launch {
            repeatUseCase(RepeatRequest(phone = phone))
                .collect { result ->
                    result.onSuccess {
                        _events.emit(Event.RepeatSuccess)
                        startTimer(60)
                    }
                    result.onFailure { e ->
                        _events.emit(Event.Error(e.message ?: "Qayta yuborib bo‘lmadi"))
                    }
                }
        }
    }
}