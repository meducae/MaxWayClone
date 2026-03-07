package uz.gita.maxwayclone.presentation.home.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.NotificationModel
import uz.gita.maxwayclone.domain.usecase.NotificationUseCase

class NotificationViewModelImpl(private val useCase: NotificationUseCase,

                                ): ViewModel(),
    NotificationViewModel {
    override val notificationsLiveData = MutableLiveData<List<NotificationModel>>()
    override val loadingLiveData= MutableLiveData<Boolean>()
    override val errorLiveData= MutableLiveData<String>()
    init {
        getNotifications()
        viewModelScope.launch {
            useCase.fetchAndSaveNotification()
        }
    }

    override fun getNotifications() {
        useCase()
            .onEach { state->
                when(state){
                    is UiState.Loading -> loadingLiveData.value = true
                    is UiState.Success->{
                        loadingLiveData.value = false
                        notificationsLiveData.value = state.data
                    }
                    is UiState.Error->{
                        loadingLiveData.value=false
                        errorLiveData
                    }
                }
            }
            .launchIn(viewModelScope)

    }

}