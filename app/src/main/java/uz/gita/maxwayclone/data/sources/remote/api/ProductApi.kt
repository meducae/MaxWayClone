package uz.gita.maxwayclone.data.sources.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.gita.maxwayclone.data.sources.remote.response.home.AdsResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.SearchResponse

interface ProductApi {
    @GET("ads")
    suspend fun getAds(): AdsResponse
    @GET("/products_by_query")
    suspend fun searchProduct(@Query("query")query: String): SearchResponse
}
// search, product, category, ads, recomen, basket, history