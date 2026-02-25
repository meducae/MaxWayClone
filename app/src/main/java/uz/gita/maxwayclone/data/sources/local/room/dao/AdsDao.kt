package uz.gita.maxwayclone.data.sources.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.sources.local.room.entity.AdsEntity

@Dao
interface AdsDao : BaseDao<AdsEntity> {

    @Query("SELECT * FROM ads_table")
    fun getAllAds(): Flow<List<AdsEntity>>

    @Query("DELETE FROM ads_table")
    suspend fun deleteAllAds()

    @Transaction
    suspend fun updateAllAds(entities: List<AdsEntity>) {
        deleteAllAds()
        insert(entities)
    }




}