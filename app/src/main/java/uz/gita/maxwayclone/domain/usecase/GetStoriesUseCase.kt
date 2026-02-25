package uz.gita.maxwayclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.StoriesModel

interface GetStoriesUseCase {
    operator fun invoke() : Flow<UiState<List<StoriesModel>>>
    suspend fun fetchAndSaveStories()
}