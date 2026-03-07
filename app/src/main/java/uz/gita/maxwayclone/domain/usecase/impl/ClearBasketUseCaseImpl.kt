package uz.gita.maxwayclone.domain.usecase.impl

import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.ClearBasketUseCase

class ClearBasketUseCaseImpl (private val repositoryImpl: AppRepositoryImpl) : ClearBasketUseCase {
    override suspend fun invoke() {
        repositoryImpl.clearBasket()
    }
}