package uz.gita.maxwayclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.AdsModel

interface AdsUseCase {
    operator fun invoke(): Flow<UiState<List<AdsModel>>>
    suspend fun fetchAndSaveAds()
}