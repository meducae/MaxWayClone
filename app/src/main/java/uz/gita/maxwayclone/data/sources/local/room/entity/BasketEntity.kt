package uz.gita.maxwayclone.data.sources.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_table")
data class BasketEntity(
    @PrimaryKey()
    val productId: Int,
    val name: String,
    val description : String,
    val imageUrl: String,
    val cost: Int,
    val count: Int=1
)