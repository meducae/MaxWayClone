package uz.gita.maxwayclone.presentation.basket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.UiState.Loading.formatPrice
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.domain.model.home.BasketModel
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.ProductTypeModel
import uz.gita.maxwayclone.domain.model.home.RcProductModel
import uz.gita.maxwayclone.domain.usecase.AddBasketItemUseCase
import uz.gita.maxwayclone.domain.usecase.ClearBasketUseCase
import uz.gita.maxwayclone.domain.usecase.CreateOrderUseCase
import uz.gita.maxwayclone.domain.usecase.DeleteBasketItemUseCase
import uz.gita.maxwayclone.domain.usecase.GetBasketItemsUseCase
import uz.gita.maxwayclone.domain.usecase.GetBasketTotalPriceUseCase
import uz.gita.maxwayclone.domain.usecase.GetRecommendedProductsUseCase
import uz.gita.maxwayclone.domain.usecase.PlusToBasketUseCase

class BasketViewModelImpl(
    private val getBasketItemsUseCase: GetBasketItemsUseCase,
    private val deleteBasketItemUseCase: DeleteBasketItemUseCase,
    private val clearBasketUseCase: ClearBasketUseCase,
    private val getBasketTotalPriceUseCase: GetBasketTotalPriceUseCase,
    private val getRecommendedUseCase: GetRecommendedProductsUseCase,
    private val addBasketItemUseCase: AddBasketItemUseCase,
    private val plusToBasketUseCase: PlusToBasketUseCase,
    private val createOrderUseCase: CreateOrderUseCase
) : ViewModel(), BasketViewModel {
    override val showBasketItems = MutableStateFlow<List<BasketModel>>(emptyList())
    override val showPrice = MutableStateFlow<String>("0")
    private val _rawRecommended = MutableStateFlow<List<RcProductModel>>(emptyList())


    private val _moveToRegister = MutableSharedFlow<Unit>()
    override val moveToRegister: SharedFlow<Unit>
        get() = _moveToRegister

    private val _showResult = MutableSharedFlow<String>()
    override val showResult: SharedFlow<String> = _showResult


    override val recommendedFlow: StateFlow<List<ProductTypeModel>> =
        combine(_rawRecommended, showBasketItems) { recommended, basketItems ->
            recommended.map { rc ->
                val count = basketItems.find { it.productId == rc.id }?.count ?: 0
                ProductTypeModel.ProductItem(
                    product = ProductModel(
                        id = rc.id,
                        categoryID = rc.categoryID,
                        name = rc.name,
                        description = rc.description,
                        image = rc.image,
                        cost = rc.cost,
                        categoryName = ""
                    ),
                    count = count
                )
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadBasketItems()
        loadRecommended()
    }

    private fun loadRecommended() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getRecommendedUseCase()
            if (result is UiState.Success) {
                _rawRecommended.value = result.data
            }
        }
    }

    override fun loadBasketItems() {
        viewModelScope.launch(Dispatchers.IO) {
            getBasketItemsUseCase().collect { state ->
                when (state) {
                    is UiState.Loading -> {}
                    is UiState.Success -> showBasketItems.value = state.data
                    is UiState.Error -> {}
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            getBasketTotalPriceUseCase().collect { totalPrice ->
                showPrice.value = totalPrice.formatPrice()
            }
        }
    }

    override fun plusBasket(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            plusToBasketUseCase(productId)
        }
    }

    override fun payOrder() {
        val token = TokenManager.getInstance().getToken()
        if (token.isEmpty()) {
            viewModelScope.launch {
                _moveToRegister.emit(Unit)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                createOrderUseCase().collect { state ->
                    when (state) {
                        is UiState.Error -> {
                            _showResult.emit(value = state.message)
                        }
                        is UiState.Success -> _showResult.emit(value = state.data.message)
                        is UiState.Loading -> {}
                    }

                }
            }
        }
    }


    override fun removeBasket(id: Int, currentCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteBasketItemUseCase(id, currentCount)
        }
    }

    override fun clearBasket() {
        viewModelScope.launch(Dispatchers.IO) {
            clearBasketUseCase()
        }
    }

    override fun addBasket(productModel: ProductModel) {
        viewModelScope.launch(Dispatchers.IO) {
            addBasketItemUseCase(productModel)
        }
    }
}