package com.example.intertech_account.model.api_model.get_customer

import com.google.gson.annotations.SerializedName

// Respondun data kısmının detayları

data class GetCustomerData(
    @SerializedName("\$type")
    var type: String,
    @SerializedName("ShortName")
    var shortName: String,
    @SerializedName("CitizenshipNumber")
    var citizenshipNumber: String,
    @SerializedName("BirthDate")
    var birthDate: String,
    @SerializedName("Email")
    var email: String,
    @SerializedName("MobilePhoneNumber")
    var getMobilePhoneList: GetMobilePhoneNumber
)