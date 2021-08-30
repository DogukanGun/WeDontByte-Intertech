package com.example.intertech_account.resources.api

import com.example.intertech_account.model.api_model.GetCurrencyModel
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import com.example.intertech_account.model.api_model.get_account.GetAccountBodyModel
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionListBodyModel
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionListModel
import com.example.intertech_account.model.api_model.get_corporate_account_transaction_list.GetCorporateAccountTransactionListBodyModel
import com.example.intertech_account.model.api_model.get_corporate_account_transaction_list.GetCorporateAccountTransactionListModel
import com.example.intertech_account.model.api_model.get_currency_body.GetCurrencyBodyModel
import com.example.intertech_account.model.api_model.get_customer.GetCustomerBodyModel
import com.example.intertech_account.model.api_model.get_customer.GetCustomerModel
import retrofit2.Response

interface ApiInterface {

    @POST("GetAccountList")
    @Headers(
        "Content-Type:application/json",
        "Ocp-Apim-Subscription-Key:e9cf2abaf9264596b62dc962b1c6a0d7",
    )
    suspend fun getAccounts(@Body getAccountBodyModel: GetAccountBodyModel): Response<GetAccountModel>

    @POST("exchange/GetCurrencyRateList")
    @Headers(
        "Content-Type:application/json",
        "Ocp-Apim-Subscription-Key:e9cf2abaf9264596b62dc962b1c6a0d7",
    )
    suspend fun getCurrencyRateList(@Body getCurrencyBody: GetCurrencyBodyModel): Response<GetCurrencyModel>

    @POST("GetCorporateAccountTransactionList")
    @Headers(
        "Content-Type:application/json",
        "Ocp-Apim-Subscription-Key:e9cf2abaf9264596b62dc962b1c6a0d7",
    )
    suspend fun getCorporateAccountTransactionList(@Body getCorporateAccountTransactionListBodyModel: GetCorporateAccountTransactionListBodyModel): Response<GetCorporateAccountTransactionListModel>

    @POST("GetAccountTransactionList")
    @Headers(
        "Content-Type:application/json",
        "Ocp-Apim-Subscription-Key:e9cf2abaf9264596b62dc962b1c6a0d7",
    )
    suspend fun getAccountTransactionList(@Body getAccountTransactionListBodyModel: GetAccountTransactionListBodyModel): Response<GetAccountTransactionListModel>

    @POST("customers/GetCustomerInfoForThirdParty")
    @Headers(
        "Content-Type:application/json",
        "Ocp-Apim-Subscription-Key:75164f09243444ff913254565353c68d",
    )
    suspend fun getCustomerInfo(@Body getCustomerInfo: GetCustomerBodyModel): Response<GetCustomerModel>
}