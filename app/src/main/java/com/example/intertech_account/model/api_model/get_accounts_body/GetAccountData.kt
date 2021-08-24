package com.example.intertech_account.model.api_model.get_accounts_body

import com.google.gson.annotations.SerializedName

data class GetAccountData(
    @SerializedName("\$type")
    var type:String,
    @SerializedName("AccountList")
    var getAccountList:Array<GetAccountList>
)