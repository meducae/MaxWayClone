package uz.gita.maxwayclone.data.sources.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_table")
data class SearchEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val image: String,
    val cost: Int
)