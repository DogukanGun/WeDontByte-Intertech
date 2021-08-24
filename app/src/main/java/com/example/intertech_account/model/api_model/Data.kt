package com.example.intertech_account.model.api_model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("\$type")
    var type:String,
    @SerializedName("AccountList")
    var accountList:Array<AccountList>
)
