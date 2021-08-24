package com.example.intertech_account.model.api_model.get_account

import com.google.gson.annotations.SerializedName

data class GetAccountModel (
    @SerializedName("type")
    var type:String,
    @SerializedName("Data")
    var getAccountData: GetAccountData


){}

