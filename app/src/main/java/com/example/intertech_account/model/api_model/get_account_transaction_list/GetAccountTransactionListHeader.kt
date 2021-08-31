package com.example.intertech_account.model.api_model.get_account_transaction_list
import com.google.gson.annotations.SerializedName

// Request yapısının header kısmı

data class GetAccountTransactionListHeader(
    @SerializedName("AppKey")
    var AppKey:String,
    @SerializedName("Channel")
    var Channel:String,
    @SerializedName("ChannelSessionId")
    var ChannelSessionId:String,
    @SerializedName("ChannelRequestId")
    var ChannelRequestId:String,
    @SerializedName("TellerName")
    var tellerName:String,
    @SerializedName("CallerId")
    var callerId:String
) {
}
