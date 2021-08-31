package com.example.intertech_account.model.api_model.get_accounts_body

import com.google.gson.annotations.SerializedName
// Responseun data bilgisi
data class GetAccountModel (
    @SerializedName("type")
    var type:String,
    @SerializedName("Data")
    var getAccountData: GetAccountData


){}

