package uz.gita.maxwayclone.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.domain.model.branch.Branch

interface BranchRepository {
    fun getBranches(): Flow<Result<List<Branch>>>
}
