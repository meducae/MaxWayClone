package uz.gita.maxwayclone.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.impl.AdsUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.impl.GetStoriesUseCaseImpl

class HomeViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val repository = AppRepositoryImpl.getInstance()
        val useCase = AdsUseCaseImpl(repository)
        val storiesUseCase = GetStoriesUseCaseImpl(repository)
        if (modelClass.isAssignableFrom(HomeViewModelImpl::class.java)) {
            return HomeViewModelImpl(useCase , storiesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}