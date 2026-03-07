package uz.gita.maxwayclone.data.sources.remote.response.create_order


data class CreateOrderResponse(
    val message: String,
    val data: CreateOrderItemResponse
)

data class CreateOrderItemResponse(
    val id: Int,
    val userID: Int,
    val ls: List<CrProductItemResponse>,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val createTime: Long,
    val sum: Long
)

data class CrProductItemResponse(
    val count: Int,
    val productData: ProductData
)

data class ProductData(
    val id: Int,
    val categoryID: Int,
    val name: String,
    val description: String,
    val image: String,
    val cost: Int
)