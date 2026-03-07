package uz.gita.maxwayclone.presentation.home.stories

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import uz.gita.maxwayclone.domain.model.home.StoriesModel
import uz.gita.maxwayclone.domain.usecase.GetStoriesUseCase

interface StoriesViewModel  {
    val showStories : Flow<List<StoriesModel>>
    fun loadStories()
}