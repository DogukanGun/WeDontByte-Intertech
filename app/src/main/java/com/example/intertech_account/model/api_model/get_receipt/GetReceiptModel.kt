package com.example.intertech_account.model.api_model.get_receipt

import com.google.gson.annotations.SerializedName

// Responseun detayları
data class GetReceiptModel (
    @SerializedName("type")
    var type: String,
    @SerializedName("Data")
    var getReceiptData: GetReceiptData
)