package uz.gita.maxwayclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.ProductTypeModel
import uz.gita.maxwayclone.domain.repository.AppRepository
import uz.gita.maxwayclone.domain.usecase.GetProductsUseCase

class GetProductsUseCaseImpl(private val repository: AppRepository) : GetProductsUseCase {
    override fun invoke(): Flow<UiState<List<ProductModel>>> = repository.getProducts()

    override suspend fun fetchAndSaveProducts() {
        repository.fetchAndSaveProducts()
    }
}