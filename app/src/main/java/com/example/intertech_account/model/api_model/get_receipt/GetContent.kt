package com.example.intertech_account.model.api_model.get_receipt

import com.google.gson.annotations.SerializedName
// Response -> Data -> Content
data class GetContent(
    @SerializedName("\$type")
    var type: String,
    @SerializedName("\$value")
    var value: String
)