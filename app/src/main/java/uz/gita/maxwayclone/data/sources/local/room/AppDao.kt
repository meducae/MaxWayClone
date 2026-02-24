package uz.gita.maxwayclone.data.sources.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDao : BaseDao<AdsEntity> {
    @Query("SELECT * FROM ads_table")
    fun getAllAds(): Flow<List<AdsEntity>>


}