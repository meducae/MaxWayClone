package uz.gita.maxwayclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.model.home.BasketModel
import uz.gita.maxwayclone.domain.usecase.GetBasketItemsUseCase

class GetBasketItemsImpl(private val repositoryImpl: AppRepositoryImpl): GetBasketItemsUseCase{
    override fun invoke(): Flow<UiState<List<BasketModel>>> {
        return repositoryImpl.getBasketItems()
    }
}