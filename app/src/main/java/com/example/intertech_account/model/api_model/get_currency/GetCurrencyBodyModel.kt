package com.example.intertech_account.model.api_model.get_currency_body

import com.google.gson.annotations.SerializedName

data class GetCurrencyBodyModel(
    @SerializedName("Header")
    var header: GetCurrencyHeader,


)