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
import uz.gita.maxwayclone.data.sources.local.room.dao.AdsDao
import uz.gita.maxwayclone.data.sources.local.room.AppDatabase
import uz.gita.maxwayclone.data.sources.local.room.dao.NotificationDao
import uz.gita.maxwayclone.data.sources.remote.api.ProductApi
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.NotificationModel
import uz.gita.maxwayclone.domain.repository.AppRepository
import kotlin.collections.emptyList

class AppRepositoryImpl private constructor(
    private val productApi: ProductApi,
    private val adsDao: AdsDao,
    private val notificationDao: NotificationDao
): AppRepository {


    companion object {
        private var instance: AppRepositoryImpl? = null


        fun getInstance(): AppRepositoryImpl {
            if (instance == null) {

                val api = ApiClient.getProductApi()
                val db = AppDatabase.getDatabase(App.instance).getDao()
                val notificationDao = AppDatabase.getDatabase(App.instance).getNotificationDao()

                instance = AppRepositoryImpl(api, db, notificationDao)
            }
            return instance!!
        }
    }

    override fun getAds(): Flow<UiState<List<AdsModel>>> =
        adsDao.getAllAds()
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


            adsDao.updateAllAds(entities)
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
}

