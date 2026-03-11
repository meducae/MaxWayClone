package uz.gita.maxwayclone.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.sources.remote.request.register.VerifyRequest
import uz.gita.maxwayclone.data.sources.remote.response.verify.ResponseVerify

interface VerifyUseCase {
    operator fun invoke(request: VerifyRequest): Flow<Result<ResponseVerify>>
}