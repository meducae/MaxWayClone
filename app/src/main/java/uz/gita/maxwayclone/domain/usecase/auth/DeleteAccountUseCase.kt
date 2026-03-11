package uz.gita.maxwayclone.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.sources.remote.response.delete_account.ResponseDeleteAccount

interface DeleteAccountUseCase {
    operator fun invoke(token: String): Flow<Result<ResponseDeleteAccount>>
}