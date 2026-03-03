package uz.gita.maxwayclone.data.sources.local

import android.content.Context
import android.content.SharedPreferences
import uz.gita.maxwayclone.app.App

object TokenManager {

    private val sharedPreferences: SharedPreferences = App.instance.getSharedPreferences("TokenManager" , Context.MODE_PRIVATE)

    fun saveToken(token : String){
        sharedPreferences.edit().putString("TOKEN" , token).apply()
    }

    fun saveName(name: String){
        sharedPreferences.edit().putString("NAME", name).apply()
    }

    fun savePhone(phone: String){
        sharedPreferences.edit().putString("PHONE", phone).apply()
    }

    fun saveBirthday(birthday: String){
        sharedPreferences.edit().putString("BIRTHDAY", birthday).apply()
    }

    fun getToken() : String{
        return sharedPreferences.getString("TOKEN" , "").toString()
    }

    fun getName() : String{
        return sharedPreferences.getString("NAME" , "").toString()
    }

    fun getPhone() : String{
        return sharedPreferences.getString("PHONE" , "").toString()
    }

    fun getBirthday(): String{
        return sharedPreferences.getString("BIRTHDAY", "").toString()
    }


    fun clear(){
        sharedPreferences.edit().clear().apply()
    }

    fun getInstance(): TokenManager{
        val instance = TokenManager
        return instance
    }
}