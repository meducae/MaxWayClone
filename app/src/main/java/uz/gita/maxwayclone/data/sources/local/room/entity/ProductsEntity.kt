package uz.gita.maxwayclone.data.sources.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductsEntity(
    @PrimaryKey()
    val id : Int,
    val categoryId : Int,
    val categoryName : String,
    val name : String,
    val description : String,
    val imgUrl : String,
    val cost : Int
)