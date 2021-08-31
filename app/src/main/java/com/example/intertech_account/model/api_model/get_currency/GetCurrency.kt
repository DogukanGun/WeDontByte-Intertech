package com.example.intertech_account.model.api_model

import com.google.gson.annotations.SerializedName

// Respondun data kısmındaki rates bilgileri

data class GetCurrency(

    @SerializedName("CurrencyCode")
    var currencyCode:String,
    @SerializedName("CrossRate")
    var crossRate:Double,
    @SerializedName("ChangeRate")
    var changeRate:Double,
    @SerializedName("ExchangeRate")
    var exchangeRate:Double,
    @SerializedName("CashChangeRate")
    var cashChangeRate:Double,
    @SerializedName("CashExchangeRate")
    var cashExchangeRate:Double,

)
{
}