package uz.gita.maxwayclone.presentation.home

import androidx.lifecycle.LiveData
import uz.gita.maxwayclone.domain.model.home.AdsModel

interface HomeViewModel {
    val loaderLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<String>
    val adsLiveData: LiveData<List<AdsModel>>

    fun fetchAds()
}