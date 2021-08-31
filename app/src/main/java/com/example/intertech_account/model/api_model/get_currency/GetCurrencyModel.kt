package com.example.intertech_account.model.api_model

import com.google.gson.annotations.SerializedName
// Respond yapısı
data class GetCurrencyModel(
    @SerializedName("type")
    var type:String,
    @SerializedName("Data")
    var dataGet:GetCurrencyData
) {}