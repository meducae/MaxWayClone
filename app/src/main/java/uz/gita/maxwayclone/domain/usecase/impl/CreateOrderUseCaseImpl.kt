package uz.gita.maxwayclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.model.home.OrderCreated
import uz.gita.maxwayclone.domain.repository.AppRepository
import uz.gita.maxwayclone.domain.usecase.CreateOrderUseCase

class CreateOrderUseCaseImpl(private val repository: AppRepository) : CreateOrderUseCase {
    override fun invoke(): Flow<UiState<OrderCreated>> {
        return repository.createOrder()
    }
}