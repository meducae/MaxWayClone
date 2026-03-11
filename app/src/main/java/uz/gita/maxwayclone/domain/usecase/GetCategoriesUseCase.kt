package uz.gita.maxwayclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.CategoryModel

interface GetCategoriesUseCase {
    operator fun  invoke() : Flow<UiState<List<CategoryModel>>>
    suspend fun fetchAndSaveCategories()
}