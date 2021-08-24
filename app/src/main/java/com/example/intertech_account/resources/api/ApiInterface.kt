package com.example.intertech_account.resources.api

import com.example.intertech_account.model.api_model.AccountModel
import com.example.intertech_account.resources.common_variables.Constant
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import com.example.intertech_account.model.api_model.get_accounts_body.BodyModel
interface ApiInterface {

    @POST("GetAccountList")
    @Headers(
        "Content-Type:application/json",
        "Ocp-Apim-Subscription-Key:e9cf2abaf9264596b62dc962b1c6a0d7",
    )
    fun getAccounts(@Body bodyModel: BodyModel): Call<AccountModel>
}