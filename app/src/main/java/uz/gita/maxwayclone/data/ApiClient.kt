package uz.gita.maxwayclone.data

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.maxwayclone.BuildConfig.BASE_URL
import uz.gita.maxwayclone.app.App.Companion.instance
import uz.gita.maxwayclone.data.sources.remote.api.AuthApi
import uz.gita.maxwayclone.data.sources.remote.api.BranchApi
import uz.gita.maxwayclone.data.sources.remote.api.ProductApi

object ApiClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ChuckerInterceptor.Builder(instance).build())
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getProductApi(): ProductApi = retrofit.create(ProductApi::class.java)
    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    fun getBranchApi(): BranchApi = retrofit.create(BranchApi::class.java)
}