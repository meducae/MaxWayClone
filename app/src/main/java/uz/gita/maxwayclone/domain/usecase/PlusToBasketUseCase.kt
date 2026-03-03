package uz.gita.maxwayclone.domain.usecase

interface PlusToBasketUseCase {
    suspend operator fun invoke(productId: Int)
}