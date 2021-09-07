package com.example.intertech_account.model.api_model.open_account

import com.google.gson.annotations.SerializedName

data class GetNewAccountResponse(

    @SerializedName("AccountName")
    var accountName: String,
    @SerializedName("IBANNo")
    var iban: String

)