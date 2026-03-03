package uz.gita.maxwayclone.data.sources.remote.response.order.my_order

data class ProductData(
    val categoryID: Int,
    val cost: Int,
    val description: String,
    val id: Int,
    val image: String,
    val name: String
)