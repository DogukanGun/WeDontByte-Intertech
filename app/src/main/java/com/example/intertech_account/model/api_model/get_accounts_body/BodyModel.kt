package com.example.intertech_account.model.api_model.get_accounts_body

import com.google.gson.annotations.SerializedName

data class BodyModel(
    @SerializedName("Header")
    var Header:Headers,
    @SerializedName("Parameters")
    var Parameters:Array<Parameters>
)
