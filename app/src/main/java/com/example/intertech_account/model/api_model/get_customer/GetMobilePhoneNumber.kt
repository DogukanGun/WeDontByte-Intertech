package com.example.intertech_account.model.api_model.get_customer

import com.google.gson.annotations.SerializedName

data class GetMobilePhoneNumber(
    @SerializedName("CityCode")
    var cityCode: String,
    @SerializedName("Number")
    var number: String,
    @SerializedName("CountryCode")
    var countryCode: String
)