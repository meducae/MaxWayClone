package uz.gita.maxwayclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.RcProductModel
import uz.gita.maxwayclone.domain.repository.AppRepository
import uz.gita.maxwayclone.domain.usecase.GetRecommendedProductsUseCase

class GetRecommendedProductsUseCaseImpl(val repository: AppRepository) : GetRecommendedProductsUseCase {
    override suspend fun invoke(): UiState<List<RcProductModel>> = repository.getRecommendedProducts()
}