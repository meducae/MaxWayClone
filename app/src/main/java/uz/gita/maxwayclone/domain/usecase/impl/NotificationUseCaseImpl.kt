package uz.gita.maxwayclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.NotificationModel
import uz.gita.maxwayclone.domain.repository.AppRepository
import uz.gita.maxwayclone.domain.usecase.NotificationUseCase

class NotificationUseCaseImpl(private val repository: AppRepository): NotificationUseCase {
    override fun invoke(): Flow<UiState<List<NotificationModel>>> {
        return repository.getNotification()
    }

    override suspend fun fetchAndSaveNotification() {
        repository.fetchAndSaveNotification()
    }
}