package uz.gita.maxwayclone.data.sources.remote.response.order.my_order

data class UserOrdersResponse(
    val data: List<UserData>,
    val message: String
)