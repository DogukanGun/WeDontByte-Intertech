package com.example.intertech_account.model.api_model.get_customer

import com.google.gson.annotations.SerializedName

// Requestin parametre detayları
data class GetCustomerParameters(

    @SerializedName("CustomerNo")
    var CustomerNo: String
)