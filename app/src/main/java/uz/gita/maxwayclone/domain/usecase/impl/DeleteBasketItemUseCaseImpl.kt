package uz.gita.maxwayclone.domain.usecase.impl

import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.repository.AppRepository
import uz.gita.maxwayclone.domain.usecase.DeleteBasketItemUseCase

class DeleteBasketItemUseCaseImpl(private val repository: AppRepository) : DeleteBasketItemUseCase {
    override suspend fun invoke(id: Int, currentCount: Int) {
        repository.decrementBasketItem(id , currentCount)
    }
}