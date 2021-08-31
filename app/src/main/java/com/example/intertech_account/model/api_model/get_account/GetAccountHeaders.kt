package com.example.intertech_account.model.api_model.get_account

import com.google.gson.annotations.SerializedName

// Request yapısının içindeki parametreler

data class GetAccountHeaders(

    @SerializedName("AppKey")
    var AppKey: String,
    @SerializedName("Channel")
    var Channel: String,
    @SerializedName("ChannelSessionId")
    var ChannelSessionId: String,
    @SerializedName("ChannelRequestId")
    var ChannelRequestId: String,
) {

}
