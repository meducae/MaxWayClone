package uz.gita.maxwayclone.domain.usecase

import uz.gita.maxwayclone.domain.model.orders.MyOrdersUIData

interface OrderUseCase {


    suspend fun getMyOrders(): Result<List<MyOrdersUIData>>

}