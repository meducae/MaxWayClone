package uz.gita.maxwayclone.data.sources.local.room.dao

import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.sources.local.room.entity.SearchEntity

interface SearchDao : BaseDao<SearchEntity>{
    @Query("select * from search_table where name like'%'||:query||'%'")
    fun searchProduct(query: String): Flow<List<SearchEntity>>
    @Query("select * from search_table")
    fun searchGetAll(): Flow<List<SearchEntity>>
    @Query("delete from search_table")
    suspend fun searchDeleteAll()
    @Transaction
    suspend fun searchUpdateAll(entity: List<SearchEntity>){
        searchDeleteAll()
        insert(entity)
    }



}