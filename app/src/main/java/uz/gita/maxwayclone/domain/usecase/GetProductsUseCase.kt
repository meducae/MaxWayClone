package uz.gita.maxwayclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.ProductTypeModel

interface GetProductsUseCase {
    operator fun invoke(): Flow<UiState<List<ProductModel>>>
    suspend fun fetchAndSaveProducts()
}