package uz.gita.maxwayclone.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.data.sources.remote.response.home.AdStoriesResponseData
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.StoriesModel

interface Repository {
    fun getAds(): Flow<UiState<List<AdsModel>>>
    fun getStories() : Flow<UiState<List<StoriesModel>>>
}