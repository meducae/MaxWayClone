package uz.gita.maxwayclone.presentation.home.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.impl.GetStoriesUseCaseImpl

class StoriesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val repository = AppRepositoryImpl.getInstance()
        val getStoriesUseCase  = GetStoriesUseCaseImpl(repository)
        return StoriesViewModelImpl(getStoriesUseCase) as T
    }
}