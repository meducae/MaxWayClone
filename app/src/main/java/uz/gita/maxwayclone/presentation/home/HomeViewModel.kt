package uz.gita.maxwayclone.presentation.home

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.StoriesModel

interface HomeViewModel {
    val loaderLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<String>
    val adsLiveData: LiveData<List<AdsModel>>
    val storiesFlowData: Flow<List<StoriesModel>>

    fun fetchAds()
    fun loadStories()
}