package com.example.intertech_account.model.api_model.open_account

import com.example.intertech_account.model.api_model.get_account_transaction_list.GetSourceAccount
import com.google.gson.annotations.SerializedName

data class GetOpenAccountParameters (

    @SerializedName("NewAccount")
    var getNewAccountResponse: GetNewAccountResponse,
    @SerializedName("Amount")
    var amount: Double,
    @SerializedName("SourceAccount")
    var getSourceAccount: GetSourceAccount,
    @SerializedName("CustomerNo")
    var customerNo: String

)