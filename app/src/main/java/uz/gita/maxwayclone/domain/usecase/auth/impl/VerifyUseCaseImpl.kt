package uz.gita.maxwayclone.domain.usecase.auth.impl

import uz.gita.maxwayclone.data.sources.remote.request.register.VerifyRequest
import uz.gita.maxwayclone.domain.repository.AuthRepository
import uz.gita.maxwayclone.domain.usecase.auth.VerifyUseCase

class VerifyUseCaseImpl(
    private val repository: AuthRepository
) : VerifyUseCase {
    override fun invoke(request: VerifyRequest) = repository.verify(request)
}