package uz.gita.maxwayclone.data.sources.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val message: String,
    val imgURL: String,
    val sendDate: String
)