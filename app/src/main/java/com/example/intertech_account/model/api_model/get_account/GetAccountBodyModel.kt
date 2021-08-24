package com.example.intertech_account.model.api_model.get_account

import com.google.gson.annotations.SerializedName

data class GetAccountBodyModel(
    @SerializedName("Header")
    var header:GetAccountHeaders,
    @SerializedName("Parameters")
    var GetAccountParameters:Array<GetAccountParameters>
)
