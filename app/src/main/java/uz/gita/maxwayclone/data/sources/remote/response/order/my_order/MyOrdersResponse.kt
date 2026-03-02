package uz.gita.maxwayclone.data.sources.remote.response.order.my_order

data class MyOrdersResponse(
    val data: List<MyData>,
    val message: String
)