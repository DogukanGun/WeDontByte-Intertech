package com.example.intertech_account.resources.api

import com.example.intertech_account.resources.common_variables.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// API requestlerinin atılması

object ApiClient {
    private var retrofit: Retrofit? = null
    fun getClient(): ApiInterface {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitBuilder.create(ApiInterface::class.java)
    }
}