package com.example.intertech_account.model.api_model.open_account

import com.google.gson.annotations.SerializedName

data class GetOpenAccountModel(
    @SerializedName("type")
    var type: String,
    @SerializedName("Data")
    var getOpenAccountData: GetOpenAccountData
)

