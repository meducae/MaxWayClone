package uz.gita.maxwayclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.RcProductModel

interface GetRecommendedProductsUseCase {
   suspend operator fun invoke(): UiState<List<RcProductModel>>
}