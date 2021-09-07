package com.example.intertech_account.model.api_model.open_account

import com.example.intertech_account.model.api_model.get_account_transaction_list.GetSourceAccount
import com.google.gson.annotations.SerializedName

data class GetOpenAccountData (
    @SerializedName("\$type")
    var type: String,
    @SerializedName("NewAccount")
    var getNewAccountResponse: GetNewAccountResponse

)