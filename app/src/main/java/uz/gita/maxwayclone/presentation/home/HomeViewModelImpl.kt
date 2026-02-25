package uz.gita.maxwayclone.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.StoriesModel
import uz.gita.maxwayclone.domain.usecase.AdsUseCase
import uz.gita.maxwayclone.domain.usecase.GetStoriesUseCase

class HomeViewModelImpl(private val adsUseCase: AdsUseCase , private val storiesUseCase: GetStoriesUseCase) : ViewModel(), HomeViewModel {
    override val loaderLiveData = MutableLiveData<Boolean>()
    override val adsLiveData = MutableLiveData<List<AdsModel>>()
    override val storiesFlowData = MutableStateFlow<List<StoriesModel>>(emptyList())
    override val errorLiveData = MutableLiveData<String>()

    override fun fetchAds() {
        adsUseCase()
            .onEach { state ->
                when (state) {
                    is UiState.Loading -> {
                        loaderLiveData.value = true
                    }
                    is UiState.Success -> {
                        loaderLiveData.value = false
                        adsLiveData.value = state.data
                    }
                    is UiState.Error -> {
                        loaderLiveData.value = false
                        errorLiveData.value = state.message
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    override fun loadStories() {
        viewModelScope.launch(Dispatchers.IO) {
            storiesUseCase().collect { state ->
                when(state){
                    is UiState.Loading -> {

                    }
                    is UiState.Success -> {
                        storiesFlowData.value = state.data
                    }
                    is UiState.Error -> {

                    }
                }

            }
        }
    }
}