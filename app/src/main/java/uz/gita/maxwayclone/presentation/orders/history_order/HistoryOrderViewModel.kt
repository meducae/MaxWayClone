package uz.gita.maxwayclone.presentation.orders.history_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.domain.model.orders.MyOrdersUIData
import uz.gita.maxwayclone.domain.usecase.OrderUseCase

class HistoryOrderViewModel (private val useCase: OrderUseCase): ViewModel(){
    val historyOrdersFlow = MutableStateFlow<List<MyOrdersUIData>>(emptyList())



    fun loadHistory() {
        viewModelScope.launch {
            val result = useCase.getMyOrders()
            result.onSuccess { allOrders ->
                val currentTime = System.currentTimeMillis()

                val historyOrders = allOrders.filter { order ->
                    val diffInMinutes = (currentTime - order.createTime) / 60_000
                    diffInMinutes >= 3
                }
                historyOrdersFlow.emit(historyOrders)
            }
        }
    }}