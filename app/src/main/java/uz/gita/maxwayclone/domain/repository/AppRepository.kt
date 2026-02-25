package uz.gita.maxwayclone.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.StoriesModel

interface AppRepository {
    fun getAds(): Flow<UiState<List<AdsModel>>>
    suspend fun fetchAndSaveAds()
    fun getStories(): Flow<UiState<List<StoriesModel>>>
    suspend fun fetchAndSaveStories()
}