package uz.gita.maxwayclone.data.sources.remote.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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
import retrofit2.Response

interface AuthApi {

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("verify")
    suspend fun verifyUser(@Body request: VerifyRequest): Response<ResponseVerify>

    @POST("repeat")
    suspend fun repeatCode(@Body request: RepeatRequest): Response<ResponseRepeat>

    @PUT("update_user_info")
    suspend fun updateUser(
        @Header("token") token: String,
        @Body requestUpdate: UpdateRequest
    ): Response<ResponseUpdate>

    @GET("user_info")
    suspend fun getUserInfo(
        @Header("token") token: String
    ): Response<ResponseUserInfo>

    @DELETE("delete_account")
    suspend fun deleteAccount(
        @Header("token") token: String
    ): Response<ResponseDeleteAccount>
}