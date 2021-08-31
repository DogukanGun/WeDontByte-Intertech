package com.example.intertech_account.model.api_model.get_account_transaction_list

import com.google.gson.annotations.SerializedName

// Respond yapısı

data class GetAccountTransactionListModel(
    @SerializedName("type")
    var type: String,
    @SerializedName("Data")
    var data: GetAccountTransactionListData

)
