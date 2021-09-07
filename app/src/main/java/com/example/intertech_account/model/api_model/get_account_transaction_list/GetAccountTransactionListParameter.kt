package com.example.intertech_account.model.api_model.get_account_transaction_list

import com.google.gson.annotations.SerializedName

// Request gönderme parametreleri
data class GetAccountTransactionListParameter(

    /*@SerializedName("CustomerNo")
    var customerNo:String,
    @SerializedName("BranchCode")
    var BranchCode:Int,
    @SerializedName("AccountSuffix")
    var accountSuffix:Int,
     */
    @SerializedName("QueryMinimumAmount")
    var queryMinimumAmount:String,
    @SerializedName("QueryMaximumAmount")
    var queryMaximumAmount:String,
    @SerializedName("ActivityCount")
    var activityCount:String,
    @SerializedName("SourceAccount")
    var getSourceAccount: GetSourceAccount,
    @SerializedName("AssociationCode")
    var associationCode: String,
    @SerializedName("QueryStartDate")
    var queryStartDate: String,
    @SerializedName("QueryEndDate")
    var queryEndDate: String

)
