package uz.gita.maxwayclone.data.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.app.App
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.data.mapper.toDomain
import uz.gita.maxwayclone.data.mapper.toEntity
import uz.gita.maxwayclone.data.mapper.toUIData
import uz.gita.maxwayclone.data.sources.local.room.AppDatabase
import uz.gita.maxwayclone.data.sources.local.room.dao.AdsDao
import uz.gita.maxwayclone.data.sources.remote.api.ProductApi
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.orders.MyOrdersUIData
import uz.gita.maxwayclone.domain.repository.AppRepository

class AppRepositoryImpl private constructor(
    private val productApi: ProductApi,
    private val dao: AdsDao
): AppRepository {


    companion object {
        private var instance: AppRepositoryImpl? = null


        fun getInstance(): AppRepositoryImpl {
            if (instance == null) {

                val api = ApiClient.getProductApi()
                val db = AppDatabase.getDatabase(App.instance).getDao()

                instance = AppRepositoryImpl(api, db)
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



    override suspend fun getMyOrders(): Result<List<MyOrdersUIData>> =
         withContext(Dispatchers.IO) {
            try {
                val token = "ef7b791a897638ca0beb06cf67be0092"
                val response = productApi.getAllOrders(token)


                val uiDataList = response.data.map { it.toUIData() }

                Result.success(uiDataList)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}

