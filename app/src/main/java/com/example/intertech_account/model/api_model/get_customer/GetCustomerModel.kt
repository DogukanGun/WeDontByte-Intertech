package com.example.intertech_account.model.api_model.get_customer

import com.google.gson.annotations.SerializedName

data class GetCustomerModel (
    @SerializedName("type")
    var type:String,
    @SerializedName("Data")
    var getCustomerData: GetCustomerData

)