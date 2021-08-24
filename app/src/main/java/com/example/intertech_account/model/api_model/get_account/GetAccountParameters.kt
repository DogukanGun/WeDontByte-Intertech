package com.example.intertech_account.model.api_model.get_account

import com.google.gson.annotations.SerializedName

data class GetAccountParameters(

    @SerializedName("CustomerNo")
    var CustomerNo:String
)
