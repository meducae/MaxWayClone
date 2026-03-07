package uz.gita.maxwayclone.data.sources.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoriesEntity (
    @PrimaryKey()
    val id : Int,
    val name : String
)