package uz.gita.maxwayclone.domain.usecase.impl

import uz.gita.maxwayclone.domain.repository.BranchRepository
import uz.gita.maxwayclone.domain.usecase.GetBranchesUseCase

class GetBranchesUseCaseImpl(
    private val repository: BranchRepository
) : GetBranchesUseCase {

    override fun invoke() = repository.getBranches()
}