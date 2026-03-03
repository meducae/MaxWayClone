package uz.gita.maxwayclone.domain.usecase.auth.impl

import uz.gita.maxwayclone.domain.repository.AuthRepository
import uz.gita.maxwayclone.domain.usecase.auth.GetUserInfoUseCase

class GetUserInfoUseCaseImpl(private val repository: AuthRepository) : GetUserInfoUseCase {
    override fun invoke(token: String) = repository.userInfo(token)
}