package uz.gita.maxwayclone.data.sources.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.maxwayclone.data.sources.local.room.dao.AdsDao
import uz.gita.maxwayclone.data.sources.local.room.dao.BasketDao
import uz.gita.maxwayclone.data.sources.local.room.dao.CategoriesDao
import uz.gita.maxwayclone.data.sources.local.room.dao.ProductsDao
import uz.gita.maxwayclone.data.sources.local.room.dao.SearchDao
import uz.gita.maxwayclone.data.sources.local.room.dao.StoriesAdsDao
import uz.gita.maxwayclone.data.sources.local.room.entity.AdsEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.BasketEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.CategoriesEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.ProductsEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.StoriesEntity
import uz.gita.maxwayclone.data.sources.local.room.entity.SearchEntity

@Database([AdsEntity::class , StoriesEntity::class , CategoriesEntity::class , ProductsEntity::class , BasketEntity::class , SearchEntity::class] , version = 6 )
abstract class AppDatabase : RoomDatabase(){
    abstract fun getDao(): AdsDao
    abstract fun getStoriesDao() : StoriesAdsDao
    abstract fun getCategoriesDao() : CategoriesDao
    abstract fun getProductsDao() : ProductsDao
    abstract fun getBasketDao() : BasketDao
    abstract fun searchDao(): SearchDao
    companion object{
        @Volatile
        private var instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppDatabase"
                )

                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}