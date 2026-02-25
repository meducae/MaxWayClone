package uz.gita.maxwayclone.data.sources.remote.api

import retrofit2.http.GET
import uz.gita.maxwayclone.data.sources.remote.response.home.AdStoriesResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.AdsResponse

interface ProductApi {
    @GET("ads")
    suspend fun getAds(): AdsResponse

    @GET("stories")
     suspend fun getStories() : AdStoriesResponse

}
// search, product, category, ads, recomen, basket, history