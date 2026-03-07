package uz.gita.maxwayclone.data.sources.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.sources.local.room.entity.ProductsEntity


@Dao
interface ProductsDao : BaseDao<ProductsEntity> {
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductsEntity>>

    @Query("DELETE FROM products")
    suspend fun deleteCategories()

    @Transaction
    suspend fun updateAllProducts(entity: List<ProductsEntity>) {
        deleteCategories()
        insert(entity)
    }
}