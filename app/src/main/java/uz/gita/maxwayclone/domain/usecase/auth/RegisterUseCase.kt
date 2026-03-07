package uz.gita.maxwayclone.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.sources.remote.request.register.RegisterRequest
import uz.gita.maxwayclone.data.sources.remote.response.register.RegisterResponse

interface RegisterUseCase {
    operator fun invoke(request: RegisterRequest): Flow<Result<RegisterResponse>>
}