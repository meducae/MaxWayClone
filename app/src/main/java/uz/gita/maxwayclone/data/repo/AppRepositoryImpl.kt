package uz.gita.maxwayclone.data.repo

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.app.App
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.data.mapper.toData
import uz.gita.maxwayclone.data.mapper.toDomain
import uz.gita.maxwayclone.data.mapper.toEntity
import uz.gita.maxwayclone.data.sources.local.room.AppDao
import uz.gita.maxwayclone.data.sources.local.room.AppDatabase
import uz.gita.maxwayclone.data.sources.remote.api.ProductApi
import uz.gita.maxwayclone.data.sources.remote.response.home.AdStoriesResponseData
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.StoriesModel
import uz.gita.maxwayclone.domain.repository.Repository

class AppRepositoryImpl private constructor(
    private val productApi: ProductApi,
    private val dao: AppDao
): Repository {

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
    override fun getAds(): Flow<UiState<List<AdsModel>>> = flow {
        emit(UiState.Loading)
        Log.d("TTT", "getAds: Keldi")
        val localData = dao.getAllAds().firstOrNull()
        if (!localData.isNullOrEmpty()) {
            emit(UiState.Success(localData.map { it.toDomain() }))
        }

        try {
            val response = productApi.getAds()
            val entities = response.data.map { it.toEntity() }
            dao.insertAds(entities)
            Log.d("TTT", "getAds: Request send ${response.message}")
            emit(UiState.Success(entities.map { it.toDomain() }))

        } catch (e: Exception) {
            if (localData.isNullOrEmpty()) {
                emit(UiState.Error(e.localizedMessage ?: "Connection error"))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getStories(): Flow<UiState<List<StoriesModel>>> =flow{
        emit(UiState.Loading)
        Log.d("TTT", "getStories: Keldi")
        val localData = dao.getStories().firstOrNull()
        if (!localData.isNullOrEmpty()){
            emit(UiState.Success(localData.map { it.toData() }))
        }

        try {
            Log.d("TTT", "getStories: TryCatch ichida")
            val response = productApi.getStories()
            Log.d("TTT", "getStories: Request send ${response.message}")
            val entities = response.data.map { it.toEntity() }
            dao.insertStories(entities)
            Log.d("TTT", "getStories: Request send ${response.message}")
            emit(UiState.Success(entities.map { it.toData() }))
        }catch (e : Exception){
            if (localData.isNullOrEmpty()){
                emit(UiState.Error(e.localizedMessage ?: "Connection error"))
            }
        }
    }.flowOn(Dispatchers.IO)


}