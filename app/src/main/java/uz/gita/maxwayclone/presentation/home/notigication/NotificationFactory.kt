package uz.gita.maxwayclone.presentation.home.notigication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.impl.NotificationUseCaseImpl

class NotificationFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = AppRepositoryImpl.getInstance()
        val useCase = NotificationUseCaseImpl(repository)
        if(modelClass.isAssignableFrom(NotificationViewModelImpl::class.java)){
            return NotificationViewModelImpl(useCase)as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}