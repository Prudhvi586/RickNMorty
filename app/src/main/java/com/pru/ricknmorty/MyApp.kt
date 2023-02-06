package com.pru.ricknmorty

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.pru.ricknmorty.controller.AppController
import com.pru.ricknmorty.db.AppDatabase
import com.pru.ricknmorty.remote.ApiService
import com.pru.ricknmorty.repository.ApiRepository
import com.pru.ricknmorty.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private var appContext_: Context? = null
val appContext: Context
    get() = appContext_
        ?: throw IllegalStateException(
            "Application context not initialized yet."
        )

private var appController_: AppController? = null
val appController: AppController
    get() = appController_ ?: throw IllegalStateException(
        "App Controller not initialized yet."
    )

private var apiRepository_: ApiRepository? = null
val apiRepository: ApiRepository
    get() = apiRepository_ ?: throw IllegalStateException(
        "ApiRepository not initialized yet."
    )

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext_ = this
        appController_ = AppController()
        val apiService = Retrofit.Builder().baseUrl(Constants.kBaseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
        val appDatabase =
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app-database")
                .fallbackToDestructiveMigration()
                .build()
        apiRepository_ = ApiRepository(apiService, appDatabase)
    }
}