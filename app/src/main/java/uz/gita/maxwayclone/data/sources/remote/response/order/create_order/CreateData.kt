package uz.gita.maxwayclone.data.sources.remote.response.order.create_order

data class CreateData(
    val address: String,
    val createTime: Long,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val ls: List<Ln>,
    val sum: Int,
    val userID: Int
)