package com.example.intertech_account.model.api_model.get_accounts_body

import com.google.gson.annotations.SerializedName
// Requestin parameters bilgileri
data class GetAccountParameter(

    @SerializedName("CustomerNo")
    var CustomerNo:String
)
