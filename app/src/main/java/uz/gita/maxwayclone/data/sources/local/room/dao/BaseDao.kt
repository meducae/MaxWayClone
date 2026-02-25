package uz.gita.maxwayclone.data.sources.local.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(data: T)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(data: List<T>)

    @Update
    suspend fun update(data: T)

    @Delete
    suspend fun delete(data: T)
}