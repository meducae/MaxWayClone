package uz.gita.maxwayclone.data.sources.remote.api

import retrofit2.http.GET
import uz.gita.maxwayclone.data.sources.remote.response.home.AdsResponse

interface ProductApi {
    @GET("ads")
    suspend fun getAds(): AdsResponse
}
// search, product, category, ads, recomen, basket, history