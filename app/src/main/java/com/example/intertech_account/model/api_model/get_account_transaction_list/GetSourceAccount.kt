package com.example.intertech_account.model.api_model.get_account_transaction_list

import com.google.gson.annotations.SerializedName

data class GetSourceAccount (
    @SerializedName("BranchCode")
    var branchCode: String,
    @SerializedName("CustomerNo")
    var CustomerNo: String,
    @SerializedName("AccountSuffix")
    var accountSuffix: String
)