package com.example.intertech_account.model.api_model.get_accounts_body

import com.google.gson.annotations.SerializedName

data class GetAccountParameter(

    @SerializedName("CustomerNo")
    var CustomerNo:String
)