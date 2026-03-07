package uz.gita.maxwayclone.domain.usecase.auth.impl

import uz.gita.maxwayclone.data.sources.remote.request.register.RepeatRequest
import uz.gita.maxwayclone.domain.repository.AuthRepository
import uz.gita.maxwayclone.domain.usecase.auth.RepeatUseCase

class RepeatUseCaseImpl(
    private val repository: AuthRepository
) : RepeatUseCase {
    override fun invoke(request: RepeatRequest) = repository.repeat(request)
}