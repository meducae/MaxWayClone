package uz.gita.maxwayclone.data.sources.remote.api

import retrofit2.Response
import retrofit2.http.GET
import uz.gita.maxwayclone.data.sources.remote.response.home.AdsResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.NotificationResponse

interface ProductApi {
    @GET("ads")
    suspend fun getAds(): AdsResponse
    @GET("notifications")
    suspend fun getNotifications(): Response<NotificationResponse>

}
// search, product, category, ads, recomen, basket, history