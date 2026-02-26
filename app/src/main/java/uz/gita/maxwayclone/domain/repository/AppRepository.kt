package uz.gita.maxwayclone.domain.repository

import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.SearchModel

interface AppRepository {
   public fun getAds(): Flow<UiState<List<AdsModel>>>
    suspend fun fetchAndSaveAds()

    fun searchProduct(query: String): Flow<UiState<List<SearchModel>>>

    suspend fun searchFetchAndSave()

}