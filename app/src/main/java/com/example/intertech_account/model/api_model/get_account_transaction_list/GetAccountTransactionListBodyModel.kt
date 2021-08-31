package com.example.intertech_account.model.api_model.get_account_transaction_list

import com.google.gson.annotations.SerializedName

// Request yapısı

data class GetAccountTransactionListBodyModel(
    @SerializedName("Header")
    var header: GetAccountTransactionListHeader,
    @SerializedName("Parameters")
    var parameters: Array<GetAccountTransactionListParameter>,

    ) {

}
