package uz.gita.maxwayclone.data.sources.remote.response.order.create_order

data class ProductDate(
    val categoryID: Int,
    val cost: Int,
    val description: String,
    val id: Int,
    val image: String,
    val name: String
)