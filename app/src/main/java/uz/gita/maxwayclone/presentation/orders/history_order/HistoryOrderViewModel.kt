package uz.gita.maxwayclone.presentation.orders.history_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.domain.model.orders.UserOrdersUIData
import uz.gita.maxwayclone.domain.usecase.OrderUseCase

class HistoryOrderViewModel (private val useCase: OrderUseCase): ViewModel(){
    val historyOrdersFlow = MutableStateFlow<List<UserOrdersUIData>>(emptyList())
     val loadingFlow = MutableStateFlow(false)
    val errorFlow = MutableStateFlow<String?>(null)



    fun loadHistory() {
        viewModelScope.launch {
            loadingFlow.emit(true)
            val result = useCase.getMyOrders()
            result.onSuccess { allOrders ->
                val currentTime = System.currentTimeMillis()

                val historyOrders = allOrders.filter { order ->
                    val diffInMinutes = (currentTime - order.createTime) / 60_000
                    diffInMinutes >= 15
                }
                historyOrdersFlow.emit(historyOrders)
                loadingFlow.emit(false)

            }
            result.onFailure {
                errorFlow.emit(it.message)
                loadingFlow.emit(false)
            }

        }
    }}