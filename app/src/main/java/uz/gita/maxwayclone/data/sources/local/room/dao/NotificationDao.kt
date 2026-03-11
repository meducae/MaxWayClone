package uz.gita.maxwayclone.data.sources.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.sources.local.room.entity.NotificationEntity

@Dao
interface NotificationDao: BaseDao<NotificationEntity> {
    @Query("SELECT * FROM notifications")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Query("DELETE FROM notifications")
    fun deleteAllNotifications()

    @Transaction
    suspend fun updateAllNotifications(entities: List<NotificationEntity>) {
        deleteAllNotifications()
        insert(entities)

    }
}