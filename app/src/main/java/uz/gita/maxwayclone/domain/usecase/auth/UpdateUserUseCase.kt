package uz.gita.maxwayclone.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.sources.remote.request.register.UpdateRequest
import uz.gita.maxwayclone.data.sources.remote.response.update.ResponseUpdate

interface UpdateUserUseCase {
    operator fun invoke(token: String, request: UpdateRequest): Flow<Result<ResponseUpdate>>
}