package uz.gita.maxwayclone.data.sources.remote.request.orders

data class CreateOrderRequest(
    val address: String = "TEST",
    val latitude: String = "41.00",
    val longitude: String = "69.00",
    val ls: List<OrderItem>
)