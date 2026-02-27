package uz.gita.maxwayclone.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.NotificationModel

interface AppRepository {
   public fun getAds(): Flow<UiState<List<AdsModel>>>
    suspend fun fetchAndSaveAds()

    fun getNotification(): Flow<UiState<List<NotificationModel>>>
    suspend fun fetchAndSaveNotification()
}