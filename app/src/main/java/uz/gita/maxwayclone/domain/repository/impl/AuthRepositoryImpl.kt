package uz.gita.maxwayclone.domain.repository.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.maxwayclone.data.sources.remote.api.AuthApi
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
import uz.gita.maxwayclone.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    override fun register(request: RegisterRequest): Flow<Result<RegisterResponse>> = flow {
        try {
            val response = api.register(request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.success(body))
                } else emit(Result.failure(Exception("Empty body")))
            } else {
                emit(Result.failure(Exception(response.message())))
            }

        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun repeat(request: RepeatRequest): Flow<Result<ResponseRepeat>> = flow {
        try {
            val response = api.repeatCode(request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.success(body))
                } else emit(Result.failure(Exception("Empty body")))
            } else {
                emit(Result.failure(Exception(response.message())))
            }

        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun verify(request: VerifyRequest): Flow<Result<ResponseVerify>> = flow {
        try {
            val response = api.verifyUser(request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.success(body))
                } else emit(Result.failure(Exception("Empty body")))
            } else {
                emit(Result.failure(Exception(response.message())))
            }

        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun update(token: String, request: UpdateRequest): Flow<Result<ResponseUpdate>> =
        flow {
            try {
                val response = api.updateUser(token, request)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {

                        emit(Result.success(body))

                    } else emit(Result.failure(Exception("Empty body")))

                } else {

                    emit(Result.failure(Exception(response.message())))

                }

            } catch (e: Exception) {

                emit(Result.failure(e))

            }
        }

    override fun userInfo(token: String): Flow<Result<ResponseUserInfo>> = flow {
        try {

            val response = api.getUserInfo(token)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.success(body))

                } else emit(Result.failure(Exception("Empty body")))

            } else {

                emit(Result.failure(Exception(response.message())))

            }

        } catch (e: Exception) {

            emit(Result.failure(e))

        }
    }

    override fun delete(token: String): Flow<Result<ResponseDeleteAccount>> = flow {
        try {
            val response = api.deleteAccount(token)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.success(body))
                } else {
                    emit(Result.failure(Exception("Empty body")))
                    api.deleteAccount(token)
                }
            } else {
                emit(Result.failure(Exception(response.message())))

            }

        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}