package com.example.api

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiModule {

    fun provideTypicodeService(baseUrl: String,
                               okHttpClient: OkHttpClient
    ): TypicodeService = createRetrofit(baseUrl, okHttpClient)
            .create(TypicodeService::class.java)

    private fun provideMoshiBuilder(): Moshi {
        return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }

    private fun createRetrofit(baseUrl: String,
                               okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(provideMoshiBuilder()))
                .client(okHttpClient)
                .build()
    }

}
