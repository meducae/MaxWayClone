package uz.gita.maxwayclone.domain.usecase.auth.impl

import uz.gita.maxwayclone.domain.repository.AuthRepository
import uz.gita.maxwayclone.domain.usecase.auth.DeleteAccountUseCase

class DeleteAccountUseCaseImpl(private val repository: AuthRepository) : DeleteAccountUseCase {
    override fun invoke(token: String) = repository.delete(token)
}