package com.example.intertech_account.model.api_model.get_corporate_account_transaction_list

import com.google.gson.annotations.SerializedName

data class GetCorporateAccountTransactionListParameter(
    @SerializedName("CustomerNo")
    var customerNo:String,
    @SerializedName("AccountBranchCode")
    var accountBranchCode:Int,
    @SerializedName("AccountSuffix")
    var accountSuffix:Int,
    @SerializedName("AssociationCode")
    var associationCode:String,
    @SerializedName("IBANNumber")
    var ibanNumber:String,
    @SerializedName("QueryDate")
    var quertDate:String,
    @SerializedName("EndDate")
    var endDate:String
) {

}