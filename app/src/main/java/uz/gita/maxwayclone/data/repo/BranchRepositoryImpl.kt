package uz.gita.maxwayclone.data.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.maxwayclone.data.mapper.toDomain
import uz.gita.maxwayclone.data.sources.remote.api.BranchApi
import uz.gita.maxwayclone.domain.model.branch.Branch
import uz.gita.maxwayclone.domain.repository.BranchRepository

class BranchRepositoryImpl(
    private val api: BranchApi
) : BranchRepository {

    override fun getBranches(): Flow<Result<List<Branch>>> = flow {

        val response = api.getBranches()

        if (response.isSuccessful) {

            val body = response.body()

            if (body != null) {

                val branches = body.data.map { it.toDomain() }

                emit(Result.success(branches))

            } else {
                emit(Result.failure(Exception("Body is empty")))
            }

        } else {
            emit(Result.failure(Exception("Error code: ${response.code()}")))
        }

    }.catch { e ->
        emit(Result.failure(e))
    }
}