package uz.gita.maxwayclone.data.sources.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.sources.local.room.entity.AdsEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.StoriesEntity

@Dao
interface StoriesAdsDao : BaseDao<StoriesEntity> {
    @Query("SELECT * FROM stories_table")
    fun getAllAds(): Flow<List<StoriesEntity>>

    @Query("DELETE FROM stories_table")
    suspend fun deleteAllAds()

    @Transaction
    suspend fun updateAllAds(entities: List<StoriesEntity>) {
        deleteAllAds()
        insert(entities)
    }
}