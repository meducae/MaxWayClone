package uz.gita.maxwayclone.domain.usecase.auth.impl

import uz.gita.maxwayclone.data.sources.remote.request.register.RegisterRequest
import uz.gita.maxwayclone.domain.repository.AuthRepository
import uz.gita.maxwayclone.domain.usecase.auth.RegisterUseCase

class RegisterUseCaseImpl(private val repository: AuthRepository) : RegisterUseCase {

    override fun invoke(request: RegisterRequest) =
        repository.register(request)
}