package com.example.intertech_account.model.api_model.get_account_transaction_list

import com.google.gson.annotations.SerializedName

// Transaction list

data class GetAccountTransactionList(
    @SerializedName("Transactions")
    var transactions: Array<GetAccountTransactionListTransactions>
) {

}
