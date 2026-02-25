package uz.gita.maxwayclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.repository.AppRepository
import uz.gita.maxwayclone.domain.usecase.AdsUseCase

class AdsUseCaseImpl  (
    private val repository: AppRepository
) : AdsUseCase {
    override  fun invoke(): Flow<UiState<List<AdsModel>>> {
        return repository.getAds()
    }

    override suspend fun fetchAndSaveAds() {
        repository.fetchAndSaveAds()

    }

}