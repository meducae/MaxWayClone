package uz.gita.maxwayclone.presentation.basket

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import uz.gita.maxwayclone.domain.model.home.BasketModel
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.ProductTypeModel

interface BasketViewModel {
    val showBasketItems : Flow<List<BasketModel>>
    val showPrice : Flow<String>
    val recommendedFlow : StateFlow<List<ProductTypeModel>>
    fun loadBasketItems()
    fun plusBasket(productId: Int)
    fun removeBasket(id : Int , currentCount : Int)
    fun clearBasket()
    fun addBasket(productModel: ProductModel)
}