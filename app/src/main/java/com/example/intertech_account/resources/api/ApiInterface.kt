package com.example.intertech_account.resources.api

import com.example.intertech_account.model.api_model.GetCurrencyModel
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import com.example.intertech_account.model.api_model.get_account.GetAccountBodyModel
import com.example.intertech_account.model.api_model.get_corporate_account_transaction_list.GetCorporateAccountTransactionListBodyModel
import com.example.intertech_account.model.api_model.get_corporate_account_transaction_list.GetCorporateAccountTransactionListModel
import com.example.intertech_account.model.api_model.get_currency_body.GetCurrencyBodyModel

interface ApiInterface {

    @POST("GetAccountList")
    @Headers(
        "Content-Type:application/json",
        "Ocp-Apim-Subscription-Key:e9cf2abaf9264596b62dc962b1c6a0d7",
    )
    fun getAccounts(@Body getAccountBodyModel: GetAccountBodyModel): Call<GetAccountModel>

    @POST("exchange/GetCurrencyRateList")
    @Headers(
        "Content-Type:application/json",
        "Ocp-Apim-Subscription-Key:e9cf2abaf9264596b62dc962b1c6a0d7",
    )
    fun getCurrencyRateList(@Body getCurrencyBody: GetCurrencyBodyModel): Call<GetCurrencyModel>

    @POST("GetCorporateAccountTransactionList")
    @Headers(
        "Content-Type:application/json",
        "Ocp-Apim-Subscription-Key:e9cf2abaf9264596b62dc962b1c6a0d7",
    )
    fun getCorporateAccountTransactionList(@Body getCorporateAccountTransactionList: GetCorporateAccountTransactionListBodyModel): Call<GetCorporateAccountTransactionListModel>

}