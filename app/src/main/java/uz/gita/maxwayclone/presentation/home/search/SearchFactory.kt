package uz.gita.maxwayclone.presentation.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.data.repo.AppRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.impl.SearchUseCaseImpl

class SearchFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = AppRepositoryImpl.getInstance()
        val useCase = SearchUseCaseImpl(repository)
        return SearchViewModelImpl(useCase) as T
    }
}