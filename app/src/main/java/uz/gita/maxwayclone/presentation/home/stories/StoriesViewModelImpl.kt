package uz.gita.maxwayclone.presentation.home.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.StoriesModel
import uz.gita.maxwayclone.domain.usecase.GetStoriesUseCase

class StoriesViewModelImpl(private val getStoriesUseCase: GetStoriesUseCase): ViewModel() , StoriesViewModel {
    override val showStories = MutableStateFlow<List<StoriesModel>>(emptyList())


    init {
        viewModelScope.launch {
            getStoriesUseCase.fetchAndSaveStories()
        }
    }
    override fun loadStories() {
        val response = getStoriesUseCase()
        response.onEach { state ->
            when(state){
                is UiState.Loading -> {

                }
                is UiState.Error -> {

                }
                is UiState.Success ->{
                    showStories.value = state.data
                }
            }
        }.launchIn(viewModelScope)
    }
}