package uz.gita.maxwayclone.data.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.app.App
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.data.mapper.toDomain
import uz.gita.maxwayclone.data.mapper.toEntity
import uz.gita.maxwayclone.data.sources.local.room.dao.AdsDao
import uz.gita.maxwayclone.data.sources.local.room.AppDatabase
import uz.gita.maxwayclone.data.sources.local.room.dao.SearchDao
import uz.gita.maxwayclone.data.sources.remote.api.ProductApi
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.SearchModel
import uz.gita.maxwayclone.domain.repository.AppRepository
import kotlin.collections.emptyList

class AppRepositoryImpl private constructor(
    private val productApi: ProductApi,
    private val dao: AdsDao,
    private val searchDao: SearchDao
): AppRepository {


    companion object {
        private var instance: AppRepositoryImpl? = null


        fun getInstance(): AppRepositoryImpl {
            if (instance == null) {

                val api = ApiClient.getProductApi()
                val db = AppDatabase.getDatabase(App.instance).getDao()
                val searchDb = AppDatabase.getDatabase(App.instance).searchDao()

                instance = AppRepositoryImpl(api, db,searchDb)
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

    override fun searchProduct(query: String): Flow<UiState<List<SearchModel>>> =
        searchDao.searchProduct(query).map { entities ->
            if(entities.isEmpty()){
                UiState.Success(emptyList())
            }else{
                UiState.Success(entities.map { it.toDomain() })
            }
        }


    override suspend fun searchFetchAndSave() {
        try {
            val response = productApi.searchProduct("a")
            val entities = response.data!!.map { it.toEntity() }
            searchDao.searchUpdateAll(entities)
        }catch (e: Exception){}
    }
}

