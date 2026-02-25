package uz.gita.maxwayclone.app

import android.app.Application
import android.content.Context
import uz.gita.maxwayclone.data.ApiClient
import uz.gita.maxwayclone.data.sources.local.room.AppDatabase

class App : Application(){

    companion object{
        lateinit var instance : Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}