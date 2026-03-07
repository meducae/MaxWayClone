package uz.gita.maxwayclone.presentation.basket

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import uz.gita.maxwayclone.domain.model.home.BasketModel
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.ProductTypeModel

interface BasketViewModel {
    val showBasketItems: Flow<List<BasketModel>>
    val showPrice: Flow<String>
    val recommendedFlow: StateFlow<List<ProductTypeModel>>
    val moveToRegister: SharedFlow<Unit>
    val showResult : SharedFlow<String>
    val getBasketItemCount : StateFlow<Int>
    fun loadBasketItems()
    fun plusBasket(productId: Int)
    fun payOrder()
    fun removeBasket(id: Int, currentCount: Int)
    fun clearBasket()
    fun addBasket(productModel: ProductModel)
}