package uz.gita.maxwayclone.domain.usecase

import uz.gita.maxwayclone.domain.model.home.ProductModel

interface AddBasketItemUseCase {
    suspend operator fun invoke(productModel: ProductModel)
}