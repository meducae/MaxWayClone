package uz.gita.maxwayclone.domain.usecase

import uz.gita.maxwayclone.domain.model.orders.UserOrdersUIData

interface OrderUseCase {


    suspend fun getMyOrders(): Result<List<UserOrdersUIData>>

}