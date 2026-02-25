package uz.gita.maxwayclone.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.AdsModel

interface AppRepository {
   public fun getAds(): Flow<UiState<List<AdsModel>>>
    suspend fun fetchAndSaveAds()
}