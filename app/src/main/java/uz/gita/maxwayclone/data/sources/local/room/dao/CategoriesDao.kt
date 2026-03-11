package uz.gita.maxwayclone.data.sources.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.sources.local.room.entity.CategoriesEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.StoriesEntity


@Dao
interface CategoriesDao: BaseDao<CategoriesEntity> {

    @Query("SELECT * FROM categories")
    fun getAllCategories() : Flow<List<CategoriesEntity>>

    @Query("DELETE FROM categories")
    suspend fun deleteCategories()

    @Transaction
    suspend fun updateAllCategories(entity: List<CategoriesEntity>){
        deleteCategories()
        insert(entity)
    }
}