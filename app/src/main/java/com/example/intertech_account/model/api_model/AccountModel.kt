package com.example.intertech_account.model.api_model

import com.google.gson.annotations.SerializedName

data class AccountModel (
    @SerializedName("type")
    var type:String,
    @SerializedName("Data")
    var data:Data


){}

