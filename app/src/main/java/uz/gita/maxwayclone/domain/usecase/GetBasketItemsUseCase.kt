package uz.gita.maxwayclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.BasketModel

interface GetBasketItemsUseCase {
    operator fun invoke() : Flow<UiState<List<BasketModel>>>
}