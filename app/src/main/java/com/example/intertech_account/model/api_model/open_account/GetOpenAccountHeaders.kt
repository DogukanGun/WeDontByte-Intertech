package com.example.intertech_account.model.api_model.open_account

import com.google.gson.annotations.SerializedName

data class GetOpenAccountHeaders(
    @SerializedName("AppKey")
    var AppKey: String,
    @SerializedName("Channel")
    var Channel: String,
    @SerializedName("ChannelSessionId")
    var ChannelSessionId: String,
    @SerializedName("ChannelRequestId")
    var ChannelRequestId: String

)