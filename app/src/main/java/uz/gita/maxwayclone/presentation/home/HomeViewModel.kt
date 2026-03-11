package uz.gita.maxwayclone.presentation.home

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.domain.model.home.AdsModel
import uz.gita.maxwayclone.domain.model.home.CategoryModel
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.model.home.ProductTypeModel
import uz.gita.maxwayclone.domain.model.home.StoriesModel

interface HomeViewModel {
    val loaderLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<String>
    val adsLiveData: LiveData<List<AdsModel>>
    val storiesFlowData: Flow<List<StoriesModel>>
    val productsFlowData : Flow<List<ProductTypeModel>>
    val categoriesFlowData : Flow<List<CategoryModel>>
    fun fetchAds()
    fun loadStories()
    fun fetchCategories()
    fun fetchProducts()
    fun addBasket(productModel: ProductModel)
    fun removeBasket(id : Int , currentCount : Int)
}