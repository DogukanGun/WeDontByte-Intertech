package com.example.intertech_account.model.api_model.open_account
import com.google.gson.annotations.SerializedName

data class GetOpenAccountBodyModel (
    @SerializedName("Header")
    var header: GetOpenAccountHeaders,
    @SerializedName("Parameters")
    var GetOpenAccountParameters: Array<GetOpenAccountParameters>
)