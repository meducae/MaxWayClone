package uz.gita.maxwayclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.SearchModel

interface SearchUseCase {
    operator fun invoke(query: String): Flow<UiState<List<SearchModel>>>

    suspend fun searchFetchAndSave()
}