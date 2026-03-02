package uz.gita.maxwayclone.data

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.maxwayclone.app.App.Companion.instance
import uz.gita.maxwayclone.data.sources.remote.api.AuthApi

object ApiClient {

    const val URL = "https://4ec0-94-158-59-159.ngrok-free.app/"
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor (ChuckerInterceptor.Builder(instance).build())
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
}