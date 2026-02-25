package uz.gita.maxwayclone.data.sources.local

import android.content.Context
import android.content.SharedPreferences
import uz.gita.maxwayclone.app.App
import androidx.core.content.edit

object TokenManager {

    val tokenPref: SharedPreferences = App.instance.getSharedPreferences("TokenManager" , Context.MODE_PRIVATE)

    fun saveToken(value : String){
        tokenPref.edit { putString("token", value) }
    }

    fun getToken() : String{
        return tokenPref.getString("token" , "").toString()
    }





}