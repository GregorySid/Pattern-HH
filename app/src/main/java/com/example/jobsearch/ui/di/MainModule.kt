package com.example.jobsearch.ui.di

import android.app.Application
import androidx.room.Room
import com.example.jobsearch.ui.apicreator.MainApi
import com.example.jobsearch.ui.apicreator.MainData
import com.example.jobsearch.ui.db.MainDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun providesBaseUrl() : String = "https://drive.usercontent.google.com/"

    @Provides
    @Singleton
    fun createRetrofit(BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun createMainApi(retrofit : Retrofit): MainApi = retrofit.create(MainApi::class.java)

    @Provides
    @Singleton
    fun provideMaineData(mainApi: MainApi): MainData = MainData(mainApi)

    @Provides
    @Singleton
    fun provideMainDb(app: Application): MainDb {
        return Room.databaseBuilder(
            app, MainDb::class.java,
            "MainDb"
        ).build()
    }

    @Provides
    fun provideFDao(bd: MainDb) = bd.favouriteDao()
}