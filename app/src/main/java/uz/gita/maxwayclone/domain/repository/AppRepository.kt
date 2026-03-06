package uz.gita.maxwayclone.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.BasketModel
import uz.gita.maxwayclone.domain.model.home.CategoryModel
import uz.gita.maxwayclone.domain.model.home.OrderCreated
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.RcProductModel
import uz.gita.maxwayclone.domain.model.home.SearchModel
import uz.gita.maxwayclone.domain.model.home.StoriesModel
import uz.gita.maxwayclone.domain.model.home.NotificationModel
import uz.gita.maxwayclone.domain.model.orders.MyOrdersUIData

interface AppRepository {
    fun getAds(): Flow<UiState<List<AdsModel>>>
    suspend fun fetchAndSaveAds()
    fun getStories(): Flow<UiState<List<StoriesModel>>>
    suspend fun fetchAndSaveStories()
    fun getProducts(): Flow<UiState<List<ProductModel>>>
    suspend fun fetchAndSaveProducts()
    fun getCategories(): Flow<UiState<List<CategoryModel>>>
    suspend fun fetchAndSaveCategories()
    suspend fun addToBasket(productModel: ProductModel)
    suspend fun plusToBasket(productId: Int)
    suspend fun decrementBasketItem(id: Int, currentCount: Int)
    fun getBasketItems(): Flow<UiState<List<BasketModel>>>
    suspend fun clearBasket()
    fun getBasketTotalPrice(): Flow<Long>
    suspend fun getRecommendedProducts(): UiState<List<RcProductModel>>
    fun createOrder(): Flow<UiState<OrderCreated>>
    fun searchProduct(query: String): Flow<UiState<List<SearchModel>>>
    suspend fun searchFetchAndSave()
    fun getBasketItemCount(): Flow<Int>
    fun getNotification(): Flow<UiState<List<NotificationModel>>>
    suspend fun fetchAndSaveNotification()


    suspend fun getMyOrders(): Result<List<MyOrdersUIData>>
}