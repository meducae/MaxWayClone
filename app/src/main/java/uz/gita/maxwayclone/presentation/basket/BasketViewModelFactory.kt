package uz.gita.maxwayclone.presentation.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.AddBasketItemUseCase
import uz.gita.maxwayclone.domain.usecase.impl.AddBasketItemImpl
import uz.gita.maxwayclone.domain.usecase.impl.ClearBasketUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.impl.CreateOrderUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.impl.DeleteBasketItemUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.impl.GetBasketItemsImpl
import uz.gita.maxwayclone.domain.usecase.impl.GetBasketTotalPriceUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.impl.GetRecommendedProductsUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.impl.PlusToBasketUseCaseImpl

class BasketViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val repository = AppRepositoryImpl.getInstance()
        val getBasketItemsUseCase = GetBasketItemsImpl(repository)
        val deleteBasketItemUseCase = DeleteBasketItemUseCaseImpl(repository)
        val clearBasketUseCase = ClearBasketUseCaseImpl(repository)
        val getBasketTotalPrice = GetBasketTotalPriceUseCaseImpl(repository)
        val getRecommendedUseCase = GetRecommendedProductsUseCaseImpl(repository)
        val addBasketItemUseCase = AddBasketItemImpl(repository)
        val plusBasketItemUseCase = PlusToBasketUseCaseImpl(repository)
        val createOrderUseCase = CreateOrderUseCaseImpl(repository)
        return BasketViewModelImpl(
            getBasketItemsUseCase,
            deleteBasketItemUseCase,
            clearBasketUseCase,
            getBasketTotalPrice,
            getRecommendedUseCase,
            addBasketItemUseCase,
            plusBasketItemUseCase,
            createOrderUseCase
        ) as T
    }
}