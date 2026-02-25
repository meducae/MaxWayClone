package uz.gita.maxwayclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.StoriesModel
import uz.gita.maxwayclone.domain.repository.Repository
import uz.gita.maxwayclone.domain.usecase.GetStoriesUseCase

class GetStoriesUseCaseImpl(private val repository: Repository) : GetStoriesUseCase{
    override fun invoke(): Flow<UiState<List<StoriesModel>>> {
        return repository.getStories()
    }

}