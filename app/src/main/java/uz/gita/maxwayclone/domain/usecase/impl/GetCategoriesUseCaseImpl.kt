package uz.gita.maxwayclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.CategoryModel
import uz.gita.maxwayclone.domain.repository.AppRepository
import uz.gita.maxwayclone.domain.usecase.GetCategoriesUseCase

class GetCategoriesUseCaseImpl(private val repository: AppRepository): GetCategoriesUseCase {
    override fun invoke(): Flow<UiState<List<CategoryModel>>> = repository.getCategories()

    override suspend fun fetchAndSaveCategories() {
        repository.fetchAndSaveCategories()
    }


}