package uz.gita.maxwayclone.domain.model.home

data class BasketModel (
    val productId: Int,
    val name: String,
    val description : String,
    val imageUrl: String,
    val cost: Int,
    val count: Int=1
)