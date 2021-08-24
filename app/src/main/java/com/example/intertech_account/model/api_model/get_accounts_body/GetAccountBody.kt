package com.example.intertech_account.model.api_model.get_accounts_body

import com.google.gson.annotations.SerializedName

data class GetAccountBody(
    @SerializedName("Header")
    var header:GetAccountHeader,
    @SerializedName("Parameters")
    var GetAccountParameter:Array<GetAccountParameter>
)
