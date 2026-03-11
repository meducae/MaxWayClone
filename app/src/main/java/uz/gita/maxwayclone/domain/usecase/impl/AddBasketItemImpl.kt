package uz.gita.maxwayclone.domain.usecase.impl

import android.util.Log
import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.model.home.ProductModel
import uz.gita.maxwayclone.domain.usecase.AddBasketItemUseCase

class AddBasketItemImpl(private val repositoryImpl: AppRepositoryImpl) : AddBasketItemUseCase {
    override suspend fun invoke(productModel: ProductModel) {
        repositoryImpl.addToBasket(productModel)
        Log.d("BTT", "invoke: ${productModel.categoryName}")
    }
}