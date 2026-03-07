package uz.gita.maxwayclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.OrderCreated

interface CreateOrderUseCase {
    operator fun invoke() : Flow<UiState<OrderCreated>>
}