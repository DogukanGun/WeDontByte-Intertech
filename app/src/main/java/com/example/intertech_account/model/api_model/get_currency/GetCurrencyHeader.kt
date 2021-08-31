package com.example.intertech_account.model.api_model.get_currency_body

import com.google.gson.annotations.SerializedName
// Requestin header kısmı
data class GetCurrencyHeader(

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
){

}
