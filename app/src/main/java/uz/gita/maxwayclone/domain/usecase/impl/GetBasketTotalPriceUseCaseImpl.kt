package uz.gita.maxwayclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.GetBasketTotalPriceUseCase

class GetBasketTotalPriceUseCaseImpl(private val repositoryImpl: AppRepositoryImpl) : GetBasketTotalPriceUseCase {
    override fun invoke(): Flow<Long> {
        return repositoryImpl.getBasketTotalPrice()
    }
}