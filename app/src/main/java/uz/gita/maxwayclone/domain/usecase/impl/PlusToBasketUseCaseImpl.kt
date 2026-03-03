package uz.gita.maxwayclone.domain.usecase.impl

import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.PlusToBasketUseCase

class PlusToBasketUseCaseImpl(private val repositoryImpl: AppRepositoryImpl) : PlusToBasketUseCase {
    override suspend fun invoke(productId: Int) {
        repositoryImpl.plusToBasket(productId)
    }
}