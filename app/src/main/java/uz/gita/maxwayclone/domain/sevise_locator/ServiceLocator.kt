package uz.gita.maxwayclone.domain.sevise_locator


import android.content.Context
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.data.sources.local.TokenManager
import uz.gita.maxwayclone.domain.repository.AuthRepository
import uz.gita.maxwayclone.domain.repository.impl.AuthRepositoryImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.DeleteAccountUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.GetUserInfoUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.RegisterUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.RepeatUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.UpdateUserUseCaseImpl
import uz.gita.maxwayclone.domain.usecase.auth.impl.VerifyUseCaseImpl
import uz.gita.maxwayclone.presentation.profile.edit_profile.EditProfileViewModelFactory
import uz.gita.maxwayclone.presentation.profile.register_code.CodeViewModelFactory
import uz.gita.maxwayclone.presentation.profile.register_phone.FragmentViewModelFactory
import uz.gita.maxwayclone.presentation.profile.ui.ProfileViewModelFactory

object ServiceLocator {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val authApi by lazy { ApiClient.api }


    private val tokenManager by lazy {
        TokenManager.getInstance()
    }


    private val repository: AuthRepository by lazy {
        AuthRepositoryImpl(authApi)
    }

    private val registerUseCase by lazy {
        RegisterUseCaseImpl(repository)
    }

    private val verifyUseCase by lazy {
        VerifyUseCaseImpl(repository)
    }
    private val repeatUseCase by lazy {
        RepeatUseCaseImpl(repository)
    }

    private val getUserInfoUseCase by lazy {
        GetUserInfoUseCaseImpl(repository)
    }
    private val updateUserUseCase by lazy {
        UpdateUserUseCaseImpl(repository)
    }
    private val deleteAccountUseCase by lazy {
        DeleteAccountUseCaseImpl(repository)
    }


    val registerVmFactory: FragmentViewModelFactory by lazy {
        FragmentViewModelFactory(registerUseCase)
    }

    val codeVmFactory by lazy {
        CodeViewModelFactory(verifyUseCase, repeatUseCase, tokenManager)
    }

    val editProfileVmFactory by lazy {
        EditProfileViewModelFactory(deleteAccountUseCase, getUserInfoUseCase, updateUserUseCase)
    }

    val profileVmFactory by lazy {
        ProfileViewModelFactory(getUserInfoUseCase)
    }

}