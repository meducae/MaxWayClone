package uz.gita.maxwayclone.domain.usecase.auth.impl

import uz.gita.maxwayclone.data.sources.remote.request.register.UpdateRequest
import uz.gita.maxwayclone.domain.repository.AuthRepository
import uz.gita.maxwayclone.domain.usecase.auth.UpdateUserUseCase

class UpdateUserUseCaseImpl(
    private val repository: AuthRepository
) : UpdateUserUseCase {
    override fun invoke(token: String, request: UpdateRequest) =
        repository.update(token, request)
}