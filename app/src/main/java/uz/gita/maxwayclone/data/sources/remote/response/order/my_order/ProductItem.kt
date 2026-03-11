package uz.gita.maxwayclone.data.sources.remote.response.order.my_order

import java.io.Serializable


data class ProductItem(
    val count: Int,
    val productData: ProductData
): Serializable