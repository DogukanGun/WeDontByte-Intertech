package com.example.intertech_account.model.api_model

import com.google.gson.annotations.SerializedName
// Respondun data kısmının içindeki bilgiler
data class GetCurrencyData(
    @SerializedName("\$type")
    var type:String,
    @SerializedName("Rates")
    var getCurrencyList:Array<GetCurrency>

) {
}