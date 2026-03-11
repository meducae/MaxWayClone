package uz.gita.maxwayclone.data.sources.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.data.sources.local.room.entity.BasketEntity
import uz.gita.maxwayclone.data.sources.remote.model.GetBasketItemOrder

@Dao
interface BasketDao {

    @Query("SELECT * FROM basket_table")
    fun getAllBasketItems(): Flow<List<BasketEntity>>

    @Query("Select productId From basket_table")
    fun getBasketItemId(): List<Int>

    @Query("Select productId , count From basket_table")
    fun getBasketOrder(): List<GetBasketItemOrder>

    @Query("SELECT COUNT(*) FROM basket_table")
    fun getBasketItemCount(): Flow<Int>


    @Query("SELECT COUNT(*) FROM basket_table")
    fun getBasketSize(): Int

    @Query("SELECT * FROM basket_table WHERE productId = :id")
    suspend fun getBasketItemById(id: Int): BasketEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: BasketEntity)

    @Query("UPDATE basket_table SET count = count + 1 WHERE productId = :id")
    suspend fun increment(id: Int)

    @Query("UPDATE BASKET_TABLE SET count = count - 1 where productId = :id")
    suspend fun decrement(id: Int)

    @Query("DELETE FROM basket_table WHERE productId = :id")
    suspend fun deleteItem(id: Int)

    @Query("SELECT SUM(cost*count) FROM basket_table")
    fun getTotalPrice(): Flow<Long>

    @Query("DELETE from basket_table")
    suspend fun deleteBasket()


}