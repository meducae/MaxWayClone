package uz.gita.maxwayclone.data.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.app.App
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.data.mapper.toDomain
import uz.gita.maxwayclone.data.mapper.toEntity
import uz.gita.maxwayclone.data.mapper.toModel
import uz.gita.maxwayclone.data.mapper.toRequest
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.data.mapper.toUIData
import uz.gita.maxwayclone.data.sources.local.room.AppDatabase
import uz.gita.maxwayclone.data.sources.local.room.dao.BasketDao
import uz.gita.maxwayclone.data.sources.local.room.dao.CategoriesDao
import uz.gita.maxwayclone.data.sources.local.room.dao.ProductsDao
import uz.gita.maxwayclone.data.sources.local.room.dao.StoriesAdsDao
import uz.gita.maxwayclone.data.sources.local.room.entity.BasketEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.ProductsEntity
import uz.gita.maxwayclone.data.sources.local.room.dao.SearchDao
import uz.gita.maxwayclone.data.sources.local.room.dao.NotificationDao
import uz.gita.maxwayclone.data.sources.local.room.dao.AdsDao
import uz.gita.maxwayclone.data.sources.remote.api.ProductApi
import uz.gita.maxwayclone.data.sources.remote.request.CreateOrder
import uz.gita.maxwayclone.data.sources.remote.request.RecommendedRequest
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.orders.UserOrdersUIData
import uz.gita.maxwayclone.domain.model.home.NotificationModel
import uz.gita.maxwayclone.domain.model.home.BasketModel
import uz.gita.maxwayclone.domain.model.home.CategoryModel
import uz.gita.maxwayclone.domain.model.home.OrderCreated
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.RcProductModel
import uz.gita.maxwayclone.domain.model.home.StoriesModel
import uz.gita.maxwayclone.domain.model.home.SearchModel
import uz.gita.maxwayclone.domain.repository.AppRepository
import kotlin.collections.emptyList

class AppRepositoryImpl private constructor(
    private val productApi: ProductApi,
    private val dao: AdsDao,
    private val notificationDao: NotificationDao,
    private val storiesDao: StoriesAdsDao,
    private val productsDao: ProductsDao,
    private val categoriesDao: CategoriesDao,
    private val basketDao: BasketDao,
    private val searchDao: SearchDao
): AppRepository {


    companion object {
        private var instance: AppRepositoryImpl? = null


        fun getInstance(): AppRepositoryImpl {
            if (instance == null) {

                val api = ApiClient.getProductApi()
                val db = AppDatabase.getDatabase(App.instance).getDao()
                val notificationDao = AppDatabase.getDatabase(App.instance).getNotificationDao()
                val storiesDao = AppDatabase.getDatabase(App.instance).getStoriesDao()
                val productsDao = AppDatabase.getDatabase(App.instance).getProductsDao()
                val categoriesDao = AppDatabase.getDatabase(App.instance).getCategoriesDao()
                val basketDao = AppDatabase.getDatabase(App.instance).getBasketDao()
                val searchDb = AppDatabase.getDatabase(App.instance).searchDao()
                instance = AppRepositoryImpl(api, db,notificationDao, storiesDao, productsDao, categoriesDao, basketDao,searchDb)
            }
            return instance!!
        }
    }

    override fun getAds(): Flow<UiState<List<AdsModel>>> =
        dao.getAllAds()
            .map { entities ->
                if (entities.isEmpty()) {
                    UiState.Success(emptyList())
                } else {
                    UiState.Success(entities.map { it.toDomain() })
                }
            }


    override suspend fun fetchAndSaveAds() = withContext(Dispatchers.IO) {
        try {
            val response = productApi.getAds()
            val entities = response.data!!.map { it.toEntity() }
            dao.updateAllAds(entities)
        } catch (e: Exception) {

        }
    }

    override fun getNotification(): Flow<UiState<List<NotificationModel>>> =
        notificationDao.getAllNotifications()
            .map { entities ->
                if (entities.isEmpty()) {
                    UiState.Success(emptyList())
                } else {
                    UiState.Success(entities.map { it.toDomain() })
                }
            }


    override suspend fun fetchAndSaveNotification() = withContext(Dispatchers.IO) {
        try {
            val response = productApi.getNotifications()
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!.data
                val entities = data.map { it.toEntity() }

                notificationDao.updateAllNotifications(entities)
            }
        } catch (e: Exception) {

        }
    }

    override suspend fun getMyOrders(): Result<List<UserOrdersUIData>> = withContext(Dispatchers.IO) {
                try {
                    val token = TokenManager.getToken()
                    val response = productApi.getAllOrders(token)
                    val uiDataList = response.data.map { it.toUIData() }
                    Result.success(uiDataList)
                } catch (e: Exception) {
                    Result.failure(e)
                }
        }

    override fun searchProduct(query: String): Flow<UiState<List<SearchModel>>> =
        searchDao.searchProduct(query).map { entities ->
          if (entities.isEmpty()) {
                UiState.Success(emptyList<SearchModel>())
            } else {
                UiState.Success(entities.map { it.toDomain() })
            }
        }

    override suspend fun searchFetchAndSave() {
        try {
            val response = productApi.searchProduct("")
            val dataList = response.data
            if (dataList != null) {
                val entities = dataList.map { it.toEntity() }
                searchDao.searchUpdateAll(entities)
            }
        } catch (e: Exception) {
        }
    }

    override fun getStories(): Flow<UiState<List<StoriesModel>>> =
        storiesDao.getAllAds().map { entities ->
            if (entities.isEmpty()) {
                UiState.Success(emptyList())
            } else {
                UiState.Success(entities.map { it.toDomain() })
            }
        }


    override suspend fun fetchAndSaveStories() {
        try {
            val response = productApi.getStories()
            val entities = response.data.map { it.toEntity() } ?: emptyList()
            storiesDao.updateAllAds(entities)
        } catch (e: Exception) {
        }
    }

    override fun getProducts(): Flow<UiState<List<ProductModel>>> {
        return productsDao.getAllProducts().map { entities ->
            if (entities.isEmpty()) {
                UiState.Success(emptyList())
            } else {
                UiState.Success(entities.map { it.toModel() })
            }
        }
    }

    override suspend fun fetchAndSaveProducts() {
        try {
            val response = productApi.getProducts()
            val entities = mutableListOf<ProductsEntity>()
            response.data.forEach { category ->
                val categoryName = category.name
                category.products.forEach { product ->
                    entities.add(
                        ProductsEntity(
                            id = product.id,
                            categoryId = product.categoryID,
                            categoryName = categoryName,
                            name = product.name,
                            description = product.description,
                            imgUrl = product.image,
                            cost = product.cost
                        )
                    )
                }
            }
            productsDao.updateAllProducts(entities)
        } catch (e: Exception) {

        }
    }

    override fun getCategories(): Flow<UiState<List<CategoryModel>>> = categoriesDao.getAllCategories().map { entities ->
        if (entities.isEmpty()) {
            UiState.Success(emptyList())
        } else {
            UiState.Success(entities.map { it.toModel() })
        }
    }

    override suspend fun fetchAndSaveCategories() {
        try {
            val response = productApi.getCategories()
            val entity = response.data.map { it.toEntity() }
            categoriesDao.updateAllCategories(entity)
        } catch (e: Exception) {

        }
    }


    override suspend fun addToBasket(productModel: ProductModel) {
        val existingItem = basketDao.getBasketItemById(productModel.id)
        if (existingItem != null) {
            basketDao.increment(productModel.id)
        } else {
            basketDao.insertItem(
                BasketEntity(
                    productId = productModel.id,
                    name = productModel.name,
                    imageUrl = productModel.image,
                    description = productModel.description,
                    cost = productModel.cost,
                    count = 1
                )
            )
        }
    }

    override suspend fun plusToBasket(productId: Int) {
        basketDao.increment(productId)
    }

    override suspend fun decrementBasketItem(id: Int, currentCount: Int) {
        if (currentCount > 1) {
            basketDao.decrement(id)
        } else {
            basketDao.deleteItem(id)
        }
    }

    override fun getBasketItems(): Flow<UiState<List<BasketModel>>> {
        val data = basketDao.getAllBasketItems().map { entities ->
            if (entities.isEmpty()) {
                UiState.Success(emptyList())
            } else {
                UiState.Success(entities.map { it.toModel() })
            }
        }
        return data
    }

    override suspend fun clearBasket() {
        basketDao.deleteBasket()
    }

    override fun getBasketTotalPrice(): Flow<Long> {
        return basketDao.getTotalPrice()
    }

    override suspend fun getRecommendedProducts(): UiState<List<RcProductModel>> {
        return try {
            val idList = basketDao.getBasketItemId()
            val response = productApi.getRecommended(RecommendedRequest(ids = idList))
            UiState.Success(response.data.map { it.toModel() })
        } catch (e: Exception) {
            UiState.Error("Xatolik")
        }
    }

    override fun createOrder(): Flow<UiState<OrderCreated>> = flow {
        try {
            val basketItemList = basketDao.getBasketSize()
            if (basketItemList != 0) {
                val basketItems = basketDao.getBasketOrder()
                val request = basketItems.map { it.toRequest() }
                val requestList = CreateOrder(ls = request, latitude = "123213123", longitude = "232342423", address = "Amerika")
                val token = TokenManager.getInstance().getToken()
                val response = productApi.createOrder(token = token, request = requestList)
                emit(UiState.Success(OrderCreated(response.message)))
                basketDao.deleteBasket()
            }else{
                emit(UiState.Success(OrderCreated("Savat bo'sh!")))
            }
        } catch (e: Exception) {
            emit(UiState.Error("Buyurtma yaratishda xatolik!"))
        }
    }
}






