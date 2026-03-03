package uz.gita.maxwayclone.data.sources.remote.request

data class CreateOrder (
    val ls : List<OrderItem>
)
data class OrderItem(
    val productID : Int,
    val count : Int,
    val latitude : String,
    val longitude : String,
    val address : String
)