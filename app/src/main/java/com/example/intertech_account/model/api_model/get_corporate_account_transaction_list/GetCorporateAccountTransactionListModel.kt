package com.example.intertech_account.model.api_model.get_corporate_account_transaction_list

import com.example.intertech_account.model.api_model.get_account.GetAccountData
import com.google.gson.annotations.SerializedName

data class GetCorporateAccountTransactionListModel(
    @SerializedName("\$type")
    var type:String,
    @SerializedName("Data")
    var data:GetCorporateAccountTransactionListData

){

}
