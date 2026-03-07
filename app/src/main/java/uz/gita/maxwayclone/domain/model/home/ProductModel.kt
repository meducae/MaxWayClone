package uz.gita.maxwayclone.domain.model.home

data class ProductModel(
    val id : Int,
    val categoryID : Int,
    val categoryName : String,
    val name : String,
    val description: String,
    val image: String,
    val cost : Int
)
