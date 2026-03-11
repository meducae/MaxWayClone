package uz.gita.maxwayclone

import uz.gita.maxwayclone.data.sources.remote.response.delete_account.ResponseDeleteAccount
import uz.gita.maxwayclone.data.sources.remote.response.register.RegisterResponse
import uz.gita.maxwayclone.data.sources.remote.response.repeat.ResponseRepeat
import uz.gita.maxwayclone.data.sources.remote.response.update.ResponseUpdate
import uz.gita.maxwayclone.data.sources.remote.response.user.ResponseUserInfo
import uz.gita.maxwayclone.data.sources.remote.response.verify.ResponseVerify

sealed class RegisterUiState{
    object Default: RegisterUiState()
    object Loading: RegisterUiState()
    data class Success(val success: RegisterResponse): RegisterUiState()
    data class Error(val error: String): RegisterUiState()
}

sealed class VerifyUiState{
    object Default: VerifyUiState()
    object Loading: VerifyUiState()
    data class Success(val success: ResponseVerify): VerifyUiState()
    data class Error(val error: String): VerifyUiState()
}

sealed class RepeatUiState{
    object Default: RepeatUiState()
    data class Success(val success: ResponseRepeat): RepeatUiState()
    data class Error(val error: String): RepeatUiState()
}

sealed class GetUserUiState{
    object Default: GetUserUiState()

    object Loading: GetUserUiState()
    data class Success(val success: ResponseUserInfo): GetUserUiState()
    data class Error(val error: String): GetUserUiState()
}

sealed class EditProfileUiState {
    data object Default : EditProfileUiState()
    data object Loading : EditProfileUiState()

    data class GetInfoSuccess(val data: ResponseUserInfo) : EditProfileUiState()
    data class UpdateSuccess(val data: ResponseUpdate) : EditProfileUiState()
    data class DeleteSuccess(val data: ResponseDeleteAccount) : EditProfileUiState()

    data class Error(val message: String) : EditProfileUiState()
}

