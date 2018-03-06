package com.example.comments

import android.app.Application
import com.example.api.ApiModule
import com.example.api.TypicodeService
import com.example.net.initJobScheduler
import com.example.store.TypicodeDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

const val SORTING_INDEX_PREF_KEY =
        BuildConfig.APPLICATION_ID + ".SORTING_INDEX_PREF_KEY"

const val FILTERING_INDEX_PREF_KEY =
        BuildConfig.APPLICATION_ID + ".FILTERING_INDEX_PREF_KEY"

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initJobScheduler(this, store, service)
    }

    val store: TypicodeDatabase by lazy {
        TypicodeDatabase.getInstance(this)
    }

    private val service: TypicodeService by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.NONE
        val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build()
        ApiModule.provideTypicodeService(BuildConfig.BASE_URL, okHttpClient)
    }

}
