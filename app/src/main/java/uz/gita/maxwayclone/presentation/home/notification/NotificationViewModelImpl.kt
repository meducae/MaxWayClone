package uz.gita.maxwayclone.presentation.home.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.UiState
import kotlinx.coroutines.delay

import uz.gita.maxwayclone.domain.model.home.NotificationModel
import uz.gita.maxwayclone.domain.usecase.NotificationUseCase

class NotificationViewModelImpl(
    private val useCase: NotificationUseCase,

    ) : ViewModel(),
    NotificationViewModel {
    override val notificationsLiveData = MutableLiveData<List<NotificationModel>>()
    override val loadingLiveData = MutableLiveData<Boolean>()
    override val errorLiveData = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            useCase.fetchAndSaveNotification()
        }
    }


    override fun getNotifications() {
        useCase()
            .onEach { state ->
                if (state is UiState.Loading) {
                    loadingLiveData.value = true
                }

                delay(2000)

                when (state) {
                    is UiState.Loading -> {
                    }

                    is UiState.Success -> {
                        loadingLiveData.value = false
                        notificationsLiveData.value = state.data
                    }

                    is UiState.Error -> {
                        loadingLiveData.value = false
                        errorLiveData.value = state.message
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}