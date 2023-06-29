package com.example.dndspellhelper.di

import android.app.Application
import androidx.room.Room
import com.example.dndspellhelper.data.local.SpellsDatabase
import com.example.dndspellhelper.data.remote.SpellsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): SpellsApi {
        return Retrofit.Builder()
            .baseUrl(SpellsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): SpellsDatabase {
        return Room.databaseBuilder(
            app,
            SpellsDatabase::class.java,
            "spells_db.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}
