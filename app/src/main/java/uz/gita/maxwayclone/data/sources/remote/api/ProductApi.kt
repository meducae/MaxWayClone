package uz.gita.maxwayclone.data.sources.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.maxwayclone.data.sources.remote.request.orders.CreateOrderRequest
import uz.gita.maxwayclone.data.sources.remote.response.home.AdsResponse
import uz.gita.maxwayclone.data.sources.remote.response.order.create_order.CreateOrderResponse
import uz.gita.maxwayclone.data.sources.remote.response.order.my_order.MyOrdersResponse

interface ProductApi {
    @GET("ads")
    suspend fun getAds(): AdsResponse

    @POST("create_order")
    suspend fun createOrder(@Header("token") token: String, @Body request: CreateOrderRequest): CreateOrderResponse

    @GET("my_orders")
    suspend fun getAllOrders(@Header("token") token:String): MyOrdersResponse

}
// search, product, category, ads, recomen, basket, history