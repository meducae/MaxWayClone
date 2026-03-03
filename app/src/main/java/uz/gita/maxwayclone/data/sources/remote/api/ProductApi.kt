package uz.gita.maxwayclone.data.sources.remote.api

import retrofit2.http.GET
import retrofit2.http.Header
import uz.gita.maxwayclone.data.sources.remote.response.home.AdsResponse
import uz.gita.maxwayclone.data.sources.remote.response.order.my_order.MyOrdersResponse

interface ProductApi {
    @GET("ads")
    suspend fun getAds(): AdsResponse


    @GET("my_orders")
    suspend fun getAllOrders(@Header("token") token:String): MyOrdersResponse

}
// search, product, category, ads, recomen, basket, history