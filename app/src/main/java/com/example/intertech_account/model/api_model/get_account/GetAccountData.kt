package com.example.intertech_account.model.api_model.get_account

import com.google.gson.annotations.SerializedName

// Account list bilgisinin çekilmesi

data class GetAccountData(
    @SerializedName("\$type")
    var type: String,
    @SerializedName("AccountList")
    var getAccountList: Array<GetAccountList>
)


//    : Comparable<GetAccountData>{
//    override fun compareTo(other: GetAccountData): Int {
//        return if ("AccountListMessage" != other.type) 1
//        else 0
//    }
//}