package com.example.intertech_account.model.api_model.get_account_transaction_list

import com.google.gson.annotations.SerializedName

data class GetAccountTransactionListData(
    @SerializedName("\$type")
    var type:String,
    @SerializedName("ActivityCollection")
    var activityCollection:Array<GetAccountTransactionList>
){

}
