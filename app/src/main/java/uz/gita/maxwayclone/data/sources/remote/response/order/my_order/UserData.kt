package uz.gita.maxwayclone.data.sources.remote.response.order.my_order

data class UserData(
    val address: String,
    val createTime: Long,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val ls: List<ProductItem>,
    val sum: Int,
    val userID: Int
)