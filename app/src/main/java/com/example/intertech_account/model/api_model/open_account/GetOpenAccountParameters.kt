package com.example.intertech_account.model.api_model.open_account

import com.google.gson.annotations.SerializedName

data class GetOpenAccountParameters(

    @SerializedName("NewAccount")
    var getNewAccountRequest: GetNewAccountRequest,
    @SerializedName("CustomerNo")
    var customerNo: String

)