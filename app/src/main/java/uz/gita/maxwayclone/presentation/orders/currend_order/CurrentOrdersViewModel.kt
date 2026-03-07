package uz.gita.maxwayclone.presentation.orders.currend_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.domain.model.orders.MyOrdersUIData
import uz.gita.maxwayclone.domain.usecase.OrderUseCase

class CurrentOrdersViewModel(private val orderUseCase: OrderUseCase) : ViewModel() {
    val ordersFlow = MutableStateFlow<List<MyOrdersUIData>>(emptyList())
    val loaderFlow = MutableStateFlow(false)
    val errorFlow = MutableSharedFlow<String>()


    val orderSuccessFlow = MutableSharedFlow<Unit>(replay = 0)

    private var pollingJob: Job? = null

    init {
        startOrderTracking()
        loadCurrentOrders()
    }
    private fun calculateStage(createTime: Long): Int {
        val diffInMinutes = (System.currentTimeMillis() - createTime) / 60_000

        return when {
            diffInMinutes < 1 -> 1
            diffInMinutes < 2 -> 2
            diffInMinutes < 3 -> 3
            else -> 4
        }
    }


    fun startOrderTracking() {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (true) {
                val result = orderUseCase.getMyOrders()

                result.onSuccess { allOrders ->
                    val updatedList = allOrders.map { order ->

                        val newStage = calculateStage(order.createTime)

                        if (newStage == 4 && order.currentStage < 4) {
                            orderSuccessFlow.tryEmit(Unit)
                        }

                        order.copy(currentStage = newStage)
                    }.filter { it.currentStage < 4 }

                    ordersFlow.emit(updatedList)
                }

                delay(10_000)
            }
        }
    }
    fun loadCurrentOrders() {
        viewModelScope.launch {
            if (ordersFlow.value.isEmpty()) loaderFlow.emit(true)

            val result = orderUseCase.getMyOrders()
            loaderFlow.emit(false)

            result.onSuccess { allOrders ->
                val currentOrders = allOrders.map { order ->
                    order.copy(
                        currentStage = calculateStage(order.createTime)
                    )
                }.filter { it.currentStage < 4 }

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