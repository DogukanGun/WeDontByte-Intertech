package com.example.intertech_account.model.api_model.get_receipt

import com.google.gson.annotations.SerializedName

// Responseun data kısmının detayları
data class GetReceiptData (
    @SerializedName("\type")
    var type: String,
    @SerializedName("Content")
    var content: GetContent
)