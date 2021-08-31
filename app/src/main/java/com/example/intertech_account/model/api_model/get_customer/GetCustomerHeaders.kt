package com.example.intertech_account.model.api_model.get_customer

import com.google.gson.annotations.SerializedName
// Request headerın detayları
data class GetCustomerHeaders (
    @SerializedName("AppKey")
    var AppKey:String,
    @SerializedName("Channel")
    var Channel:String,
    @SerializedName("ChannelSessionId")
    var ChannelSessionId:String,
    @SerializedName("ChannelRequestId")
    var ChannelRequestId:String,
)