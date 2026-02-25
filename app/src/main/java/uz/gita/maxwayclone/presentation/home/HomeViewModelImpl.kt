package uz.gita.maxwayclone.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.usecase.AdsUseCase

class HomeViewModelImpl(private val adsUseCase: AdsUseCase) : ViewModel(), HomeViewModel {
    override val loaderLiveData = MutableLiveData<Boolean>()
    override val adsLiveData = MutableLiveData<List<AdsModel>>()
    override val errorLiveData = MutableLiveData<String>()

    init {
        fetchAds()
    }

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
    }}