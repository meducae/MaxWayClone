package uz.gita.maxwayclone.domain.usecase

import uz.gita.maxwayclone.data.sources.remote.request.orders.CreateOrderRequest
import uz.gita.maxwayclone.data.sources.remote.response.order.create_order.CreateOrderResponse
import uz.gita.maxwayclone.domain.model.orders.MyOrdersUIData

interface OrderUseCase {

    suspend fun confirmOrder(request: CreateOrderRequest): Result<CreateOrderResponse>

    suspend fun getMyOrders(): Result<List<MyOrdersUIData>>

    fun clearCart()
}