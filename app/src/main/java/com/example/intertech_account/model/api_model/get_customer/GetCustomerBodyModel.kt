package com.example.intertech_account.model.api_model.get_customer


import com.google.gson.annotations.SerializedName
// Request yapısı

data class GetCustomerBodyModel (
    @SerializedName("Header")
    var header: GetCustomerHeaders,
    @SerializedName("Parameters")
    var GetCustomerParameters:Array<GetCustomerParameters>
)