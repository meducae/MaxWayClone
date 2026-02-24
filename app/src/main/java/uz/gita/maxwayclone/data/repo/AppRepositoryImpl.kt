package uz.gita.maxwayclone.data.repo

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.app.App
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.data.mapper.toDomain
import uz.gita.maxwayclone.data.mapper.toEntity
import uz.gita.maxwayclone.data.sources.local.room.AppDao
import uz.gita.maxwayclone.data.sources.local.room.AppDatabase
import uz.gita.maxwayclone.data.sources.remote.api.ProductApi
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.repository.AppRepository

class AppRepositoryImpl private constructor(
    private val productApi: ProductApi,
    private val dao: AppDao
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
            }.flowOn(Dispatchers.IO)


    override suspend fun fetchAndSaveAds() {
        try {
            val response = productApi.getAds()
            val entities = response.data!!.map { it.toEntity() }


            dao.insert(entities)
        } catch (e: Exception) {

            Log.e("AdsRepository", "Ads fetch failed: ${e.message}")
        }
    }
}

