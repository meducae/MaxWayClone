package uz.gita.maxwayclone.data.sources.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stories_table")
data class StoriesEntity (
    @PrimaryKey()
    val id : Int,
    val name : String,
    val url : String
)