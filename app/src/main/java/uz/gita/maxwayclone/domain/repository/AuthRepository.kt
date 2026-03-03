package uz.gita.maxwayclone.domain.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import uz.gita.maxwayclone.data.sources.remote.request.register.RegisterRequest
import uz.gita.maxwayclone.data.sources.remote.request.register.RepeatRequest
import uz.gita.maxwayclone.data.sources.remote.request.register.UpdateRequest
import uz.gita.maxwayclone.data.sources.remote.request.register.VerifyRequest
import uz.gita.maxwayclone.data.sources.remote.response.delete_account.ResponseDeleteAccount
import uz.gita.maxwayclone.data.sources.remote.response.register.RegisterResponse
import uz.gita.maxwayclone.data.sources.remote.response.repeat.ResponseRepeat
import uz.gita.maxwayclone.data.sources.remote.response.update.ResponseUpdate
import uz.gita.maxwayclone.data.sources.remote.response.user.ResponseUserInfo
import uz.gita.maxwayclone.data.sources.remote.response.verify.ResponseVerify

interface AuthRepository {

    fun register(request: RegisterRequest): Flow<Result<RegisterResponse>>

    fun repeat(request: RepeatRequest): Flow<Result<ResponseRepeat>>

    fun verify(request: VerifyRequest): Flow<Result<ResponseVerify>>

    fun update(token: String ,request: UpdateRequest): Flow<Result<ResponseUpdate>>

    fun userInfo(token: String): Flow<Result<ResponseUserInfo>>

    fun delete(token: String): Flow<Result<ResponseDeleteAccount>>

}