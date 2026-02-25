package uz.gita.maxwayclone.data.sources.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ads_table")
data class AdsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val imageUrl: String
)