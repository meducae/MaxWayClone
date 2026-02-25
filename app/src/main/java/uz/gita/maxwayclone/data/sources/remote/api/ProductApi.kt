package uz.gita.maxwayclone.data.sources.remote.api

import retrofit2.http.GET
import uz.gita.maxwayclone.data.sources.remote.response.home.AdsResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.AdsStoriesResponse

interface ProductApi {
    @GET("ads")
    suspend fun getAds(): AdsResponse

    @GET("stories")
     suspend fun getStories() : AdsStoriesResponse

}
// search, product, category, ads, recomen, basket, history