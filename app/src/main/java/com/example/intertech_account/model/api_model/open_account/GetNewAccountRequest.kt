package com.example.intertech_account.model.api_model.open_account

import com.google.gson.annotations.SerializedName

data class GetNewAccountRequest (
    @SerializedName("AccountName")
    var accountName: String,
    @SerializedName("AccountSuffix")
    var accountSuffix: String,
    @SerializedName("BranchCode")
    var branchCode: String,
    @SerializedName("CurrencyCode")
    var currencyCode: String,
    @SerializedName("CustomerNo")
    var customerNo: String,
)