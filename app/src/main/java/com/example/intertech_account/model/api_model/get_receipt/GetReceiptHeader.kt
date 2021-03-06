package com.example.intertech_account.model.api_model.get_receipt

import com.google.gson.annotations.SerializedName

// Requestin header kısmı

data class GetReceiptHeader(
    @SerializedName("AppKey")
    var AppKey: String,
    @SerializedName("Channel")
    var Channel: String,
    @SerializedName("ChannelSessionId")
    var ChannelSessionId: String,
    @SerializedName("ChannelRequestId")
    var ChannelRequestId: String,
)