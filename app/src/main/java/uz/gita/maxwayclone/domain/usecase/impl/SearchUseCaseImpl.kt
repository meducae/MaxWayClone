package uz.gita.maxwayclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.SearchModel
import uz.gita.maxwayclone.domain.repository.AppRepository
import uz.gita.maxwayclone.domain.usecase.SearchUseCase

class SearchUseCaseImpl(private val repository: AppRepository) : SearchUseCase{
    override fun invoke(query: String): Flow<UiState<List<SearchModel>>> {
        return repository.searchProduct(query)
    }

    override suspend fun searchFetchAndSave() {
        repository.searchFetchAndSave()
    }
}