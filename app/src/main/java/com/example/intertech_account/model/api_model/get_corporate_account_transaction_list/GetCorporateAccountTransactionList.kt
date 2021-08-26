package com.example.intertech_account.model.api_model.get_corporate_account_transaction_list

import com.google.gson.annotations.SerializedName

data class GetCorporateAccountTransactionList(
    @SerializedName("Transactions")
    var transactions:Array<GetCorporateAccountTranscationListTransactions>
){

}
