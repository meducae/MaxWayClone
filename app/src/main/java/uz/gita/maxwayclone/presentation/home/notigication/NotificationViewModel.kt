package uz.gita.maxwayclone.presentation.home.notigication

import androidx.lifecycle.LiveData
import uz.gita.maxwayclone.domain.model.home.NotificationModel

interface NotificationViewModel {
    val notificationsLiveData: LiveData<List<NotificationModel>>
    val loadingLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<String>

    fun getNotifications()

}