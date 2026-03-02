package uz.gita.maxwayclone.domain.usecase.impl

import uz.gita.maxwayclone.data.sources.remote.request.orders.CreateOrderRequest
import uz.gita.maxwayclone.data.sources.remote.response.order.create_order.CreateOrderResponse
import uz.gita.maxwayclone.domain.model.orders.MyOrdersUIData
import uz.gita.maxwayclone.domain.repository.AppRepository
import uz.gita.maxwayclone.domain.usecase.OrderUseCase

class OrderUseCaseImpl(
    private val repository: AppRepository
) : OrderUseCase {

    override suspend fun confirmOrder(request: CreateOrderRequest): Result<CreateOrderResponse> {
        return repository.confirmOrder(request)
    }

    override suspend fun getMyOrders(): Result<List<MyOrdersUIData>> {
        return repository.getMyOrders()
    }

    override fun clearCart() {
        repository.clearCart()
    }

}