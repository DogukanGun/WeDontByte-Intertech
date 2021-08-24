package com.example.intertech_account.model.api_model.get_accounts_body

import com.google.gson.annotations.SerializedName

data class Headers(

    @SerializedName("AppKey")
    var AppKey:String,
    @SerializedName("Channel")
    var Channel:String,
    @SerializedName("ChannelSessionId")
    var ChannelSessionId:String,
    @SerializedName("ChannelRequestId")
    var ChannelRequestId:String,
){

}
