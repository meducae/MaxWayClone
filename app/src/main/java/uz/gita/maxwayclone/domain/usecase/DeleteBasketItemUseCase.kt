package uz.gita.maxwayclone.domain.usecase

interface DeleteBasketItemUseCase {
    suspend operator fun invoke(id : Int  , currentCount : Int)
}