package uz.gita.maxwayclone.presentation.branch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayclone.data.repo.BranchRepositoryImpl
import uz.gita.maxwayclone.data.sources.remote.api.AuthApi
import uz.gita.maxwayclone.data.sources.remote.api.BranchApi
import uz.gita.maxwayclone.domain.repository.BranchRepository
import uz.gita.maxwayclone.domain.usecase.GetBranchesUseCase
import uz.gita.maxwayclone.domain.usecase.impl.GetBranchesUseCaseImpl

class BranchesViewModelFactory(
    private val api: BranchApi
) : ViewModelProvider.Factory {

    private val repository: BranchRepository = BranchRepositoryImpl(api)
    private val useCase: GetBranchesUseCase = GetBranchesUseCaseImpl(repository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BranchesViewModel::class.java)) {
            return BranchesViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}