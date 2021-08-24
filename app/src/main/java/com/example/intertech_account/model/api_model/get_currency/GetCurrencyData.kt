package com.example.intertech_account.model.api_model

import com.google.gson.annotations.SerializedName

data class GetCurrencyData(
    @SerializedName("\$type")
    var type:String,
    @SerializedName("Rates")
    var getCurrencyList:Array<GetCurrency>

) {
}