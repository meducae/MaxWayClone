package uz.gita.maxwayclone.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayclone.data.sources.remote.response.user.ResponseUserInfo

interface GetUserInfoUseCase {
    operator fun invoke(token: String): Flow<Result<ResponseUserInfo>>
}