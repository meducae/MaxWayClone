package uz.gita.maxwayclone.presentation.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.UiState
import uz.gita.maxwayclone.data.mapper.toTypeModel
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.CategoryModel
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.ProductTypeModel
import uz.gita.maxwayclone.domain.model.home.StoriesModel
import uz.gita.maxwayclone.domain.usecase.AddBasketItemUseCase
import uz.gita.maxwayclone.domain.usecase.AdsUseCase
import uz.gita.maxwayclone.domain.usecase.DeleteBasketItemUseCase
import uz.gita.maxwayclone.domain.usecase.GetBasketItemsUseCase
import uz.gita.maxwayclone.domain.usecase.GetCategoriesUseCase
import uz.gita.maxwayclone.domain.usecase.GetProductsUseCase
import uz.gita.maxwayclone.domain.usecase.GetStoriesUseCase

class HomeViewModelImpl(
    private val adsUseCase: AdsUseCase,
    private val storiesUseCase: GetStoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getBasketItems: GetBasketItemsUseCase,
    private val addBasketItemUseCase: AddBasketItemUseCase,
    private val deleteUseCase: DeleteBasketItemUseCase
) : ViewModel(), HomeViewModel {
    override val loaderLiveData = MutableLiveData<Boolean>()
    override val adsLiveData = MutableLiveData<List<AdsModel>>()
    override val storiesFlowData = MutableStateFlow<List<StoriesModel>>(emptyList())
    override val productsFlowData = MutableStateFlow<List<ProductTypeModel>>(emptyList())
    override val categoriesFlowData = MutableStateFlow<List<CategoryModel>>(emptyList())
    override val errorLiveData = MutableLiveData<String>()

    init {
        fetchAds()
        loadStories()
        fetchProducts()
        fetchCategories()
        viewModelScope.launch {
            adsUseCase.fetchAndSaveAds()
            storiesUseCase.fetchAndSaveStories()
            getProductsUseCase.fetchAndSaveProducts()
            getCategoriesUseCase.fetchAndSaveCategories()
        }
    }

    override fun fetchAds() {
        adsUseCase()
            .onEach { state ->
                when (state) {
                    is UiState.Loading -> {
                        loaderLiveData.value = true
                    }

                    is UiState.Success -> {
                        loaderLiveData.value = false
                        adsLiveData.value = state.data
                    }

                    is UiState.Error -> {
                        loaderLiveData.value = false
                        errorLiveData.value = state.message
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    override fun loadStories() {
        viewModelScope.launch(Dispatchers.IO) {
            storiesUseCase().collect { state ->
                when (state) {
                    is UiState.Loading -> {

                    }

                    is UiState.Success -> {
                        storiesFlowData.value = state.data
                    }

                    is UiState.Error -> {

                    }
                }

            }
        }
    }

    override fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            getCategoriesUseCase().collect { state ->
                when (state) {
                    is UiState.Loading -> {

                    }

                    is UiState.Success -> {
                        categoriesFlowData.value = state.data
                    }

                    is UiState.Error -> {

                    }
                }
            }
        }
    }

    override fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val productsUseCaseFlow = getProductsUseCase()
            val basketsUseCaseFlow = getBasketItems()
            productsUseCaseFlow.combine(basketsUseCaseFlow) { products, baskets ->
                if (products is UiState.Success && baskets is UiState.Success) {
                    val productsData = products.data
                    val basketsData = baskets.data
                    val uiModelList: List<ProductTypeModel> = productsData.toTypeModel(basketsData)

                    return@combine UiState.Success(uiModelList)
                } else if (products is UiState.Loading) {
                    return@combine UiState.Loading
                } else {
                    return@combine UiState.Error("Server xatoligi")
                }
            }.collectLatest { combinedState ->
                when (combinedState) {
                    is UiState.Success -> {
                        productsFlowData.value = combinedState.data
                    }

                    is UiState.Error -> { /* Xatolik kelsa ekranga chiqarish */
                    }

                    is UiState.Loading -> { /* Loader */
                    }
                }
            }
        }
    }

    override fun addBasket(productModel: ProductModel) {
        viewModelScope.launch(Dispatchers.IO) {
            addBasketItemUseCase(productModel)
        }
    }

    override fun removeBasket(id: Int, currentCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUseCase(id, currentCount)
        }
    }
}