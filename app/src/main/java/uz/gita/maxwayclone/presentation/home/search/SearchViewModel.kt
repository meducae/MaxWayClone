package uz.gita.maxwayclone.presentation.home.search

import androidx.lifecycle.LiveData
import uz.gita.maxwayclone.domain.model.home.SearchModel

interface SearchViewModel {
    val loaderLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<String>
    val searchResultLiveData: LiveData<List<SearchModel>>

    fun search(query: String)
}