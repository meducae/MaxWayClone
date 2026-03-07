package uz.gita.maxwayclone.presentation.orders.history_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.impl.OrderUseCaseImpl

class HistoryOrderFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val repository = AppRepositoryImpl.getInstance()
        val useCase = OrderUseCaseImpl(repository)

        if (modelClass.isAssignableFrom(HistoryOrderViewModel::class.java)) {
            return HistoryOrderViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}