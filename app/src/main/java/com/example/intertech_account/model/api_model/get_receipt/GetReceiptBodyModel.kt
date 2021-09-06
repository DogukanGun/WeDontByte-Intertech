package com.example.intertech_account.model.api_model.get_receipt

import com.google.gson.annotations.SerializedName

// Request yapısı
data class GetReceiptBodyModel (
    @SerializedName("Header")
    var header: GetReceiptHeader,
    @SerializedName("Parameters")
    var GetReceiptParameters: Array<GetReceiptParameters>
)