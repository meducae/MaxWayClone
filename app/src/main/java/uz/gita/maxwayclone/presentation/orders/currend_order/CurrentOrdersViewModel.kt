package uz.gita.maxwayclone.presentation.orders.currend_order

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.data.sources.remote.request.orders.CreateOrderRequest
import uz.gita.maxwayclone.data.sources.remote.request.orders.OrderItem
import uz.gita.maxwayclone.domain.model.orders.MyOrdersUIData
import uz.gita.maxwayclone.domain.usecase.OrderUseCase

class CurrentOrdersViewModel(private val orderUseCase: OrderUseCase) : ViewModel() {
    val ordersFlow = MutableStateFlow<List<MyOrdersUIData>>(emptyList())
    val loaderFlow = MutableStateFlow<Boolean>(false)
    val errorFlow = MutableSharedFlow<String>()

    // SHU QATORNI QO'SHING:
    val orderSuccessFlow = MutableSharedFlow<Unit>(replay = 0)

    private var pollingJob: Job? = null

    fun startOrderTracking() {
        pollingJob?.cancel()
        pollingJob = viewModelScope.launch {
            val result = orderUseCase.getMyOrders()

            result.onSuccess { allOrders ->
                // ToMutableList() qilib olamizki, ichidagi ma'lumotlarni o'zgartira olaylik
                var currentList = allOrders.filter { it.currentStage < 4 }.toMutableList()
                ordersFlow.emit(currentList)

                while (currentList.isNotEmpty()) {
                    delay(300_000) // 1 minut kutish

                    val nextStepList = currentList.map { order ->
                        val nextStage = order.currentStage + 1

                        if (nextStage == 4) {
                            // tryEmit orqali Fragmentga Toast uchun xabar yuboramiz
                            orderSuccessFlow.tryEmit(Unit)
                        }

                        // Statusni bittaga oshirib copy qilamiz
                        order.copy(currentStage = nextStage)
                    }.filter { it.currentStage < 4 } // 4 bo'lganlari avtomatik chiqib ketadi

                    currentList = nextStepList.toMutableList()
                    ordersFlow.emit(currentList)
                }
            }
        }
    }

    fun loadCurrentOrders() {
        viewModelScope.launch {
            if (ordersFlow.value.isEmpty()) loaderFlow.emit(true)

            val result = orderUseCase.getMyOrders()
            loaderFlow.emit(false)

            result.onSuccess { allOrders ->
                val currentOrders = allOrders.filter { it.currentStage < 4 }
                ordersFlow.emit(currentOrders)
            }.onFailure {
                errorFlow.emit(it.message ?: "Xatolik yuz berdi")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        pollingJob?.cancel()
    }
}