package uz.gita.maxwayclone.data.sources.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.maxwayclone.data.sources.remote.request.CreateOrder
import uz.gita.maxwayclone.data.sources.remote.request.RecommendedRequest
import uz.gita.maxwayclone.data.sources.remote.response.create_order.CreateOrderResponse
import retrofit2.http.Query
import uz.gita.maxwayclone.data.sources.remote.response.home.AdsResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.AdsStoriesResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.CategoriesResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.GeneralResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.ProductResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.ProductsResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.SearchResponse
import uz.gita.maxwayclone.data.sources.remote.response.home.NotificationResponse
import uz.gita.maxwayclone.data.sources.remote.response.order.my_order.UserOrdersResponse

interface ProductApi {
    @GET("ads")
    suspend fun getAds(): AdsResponse

    @GET("stories")
    suspend fun getStories(): AdsStoriesResponse

    @GET("products_by_category")
    suspend fun getProducts(): GeneralResponse<ProductsResponse>

    @GET("categories")
    suspend fun getCategories(): GeneralResponse<CategoriesResponse>

    @POST("recommended_products")
    suspend fun getRecommended(@Body ids: RecommendedRequest): GeneralResponse<ProductResponse>

    @POST("create_order")
    suspend fun createOrder(@Header("token") token: String, @Body request: CreateOrder): CreateOrderResponse

    @GET("/products_by_query")
    suspend fun searchProduct(@Query("query") query: String): SearchResponse

    @GET("notifications")
    suspend fun getNotifications(): Response<NotificationResponse>
    @GET("my_orders")
    suspend fun getAllOrders(@Header("token") token:String): UserOrdersResponse

}
// search, product, category, ads, recomen, basket, history