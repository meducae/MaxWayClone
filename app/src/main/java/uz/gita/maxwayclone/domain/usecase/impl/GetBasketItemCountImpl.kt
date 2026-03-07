package uz.gita.maxwayclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.domain.repository.AppRepository
import uz.gita.maxwayclone.domain.usecase.GetBasketItemCountUseCase

class GetBasketItemCountImpl(private val appRepository: AppRepository) : GetBasketItemCountUseCase {
    override fun invoke(): Flow<Int> {
        return appRepository.getBasketItemCount()
    }
}