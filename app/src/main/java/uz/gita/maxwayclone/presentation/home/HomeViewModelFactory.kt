package uz.gita.maxwayclone.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.impl.AddBasketItemImpl
import uz.gita.maxwayclone.domain.usecase.impl.AdsUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.impl.DeleteBasketItemUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.impl.GetBasketItemsImpl
import uz.gita.maxwayclone.domain.usecase.impl.GetCategoriesUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.impl.GetProductsUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.impl.GetStoriesUseCaseImpl

class HomeViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val repository = AppRepositoryImpl.getInstance()
        val useCase = AdsUseCaseImpl(repository)
        val storiesUseCase = GetStoriesUseCaseImpl(repository)
        val productsUseCase = GetProductsUseCaseImpl(repository)
        val categoriesUseCase = GetCategoriesUseCaseImpl(repository)
        val getBasketItemsUseCase = GetBasketItemsImpl(repository)
        val addBasketItemUseCase = AddBasketItemImpl(repository)
        val deleteBasketItemUseCase = DeleteBasketItemUseCaseImpl(repository)
        return HomeViewModelImpl(useCase, storiesUseCase, productsUseCase, categoriesUseCase, getBasketItemsUseCase, addBasketItemUseCase, deleteBasketItemUseCase) as T

    }
}