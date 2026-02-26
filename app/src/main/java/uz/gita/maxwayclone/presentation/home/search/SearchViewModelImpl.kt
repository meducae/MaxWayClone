package uz.gita.maxwayclone.presentation.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.SearchModel
import uz.gita.maxwayclone.domain.usecase.SearchUseCase

class SearchViewModelImpl(private val useCase: SearchUseCase,

): ViewModel(), SearchViewModel {
    override val loaderLiveData= MutableLiveData<Boolean>()
    override val errorLiveData= MutableLiveData<String>()
    override val searchResultLiveData= MutableLiveData<List<SearchModel>>()

    init {
        viewModelScope.launch {
            useCase.searchFetchAndSave()
        }
    }
    override fun search(query: String) {
        useCase(query)
            .onEach { state ->
                when(state){
                    is UiState.Loading -> loaderLiveData.value = true
                    is UiState.Success->{
                        loaderLiveData.value = false
                        searchResultLiveData.value = state.data
                    }
                    is UiState.Error -> {
                        loaderLiveData.value = false
                        errorLiveData.value = state.message
                    }

                }
            }.launchIn(viewModelScope)
    }


}