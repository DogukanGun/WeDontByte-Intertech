package com.example.intertech_account.resources.api

import android.icu.util.TimeUnit
import com.example.intertech_account.resources.common_variables.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// API requestlerinin atılması

object ApiClient {
    private val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build()

//
    private var retrofit: Retrofit? = null
    fun getClient(): ApiInterface {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitBuilder.create(ApiInterface::class.java)
    }
}