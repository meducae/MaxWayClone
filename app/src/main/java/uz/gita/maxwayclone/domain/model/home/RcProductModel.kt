package uz.gita.maxwayclone.domain.model.home

data class RcProductModel(
    val id: Int,
    val categoryID: Int,
    val name: String,
    val description: String,
    val image: String,
    val cost: Int
)