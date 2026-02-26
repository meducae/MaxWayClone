package uz.gita.maxwayclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.domain.model.branch.Branch

interface GetBranchesUseCase {
    operator fun invoke(): Flow<Result<List<Branch>>>
}