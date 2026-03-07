package uz.gita.maxwayclone.domain.usecase.impl

import uz.gita.maxwayclone.domain.model.orders.UserOrdersUIData
import uz.gita.maxwayclone.domain.repository.AppRepository
import uz.gita.maxwayclone.domain.usecase.OrderUseCase

class OrderUseCaseImpl(
    private val repository: AppRepository
) : OrderUseCase {



    override suspend fun getMyOrders(): Result<List<UserOrdersUIData>> {
        return repository.getMyOrders()
    }



}