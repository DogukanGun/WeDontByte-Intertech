package com.example.intertech_account.model.api_model

import com.google.gson.annotations.SerializedName

data class AccountList(
    @SerializedName("IsBlockedAccount")
    var isBlocked:Boolean,
    @SerializedName("BranchName")
    var branch:String,
    @SerializedName("ShortName")
    var name:String,
    @SerializedName("IsClosed")
    var isClosed:Boolean,
    @SerializedName("CurrencyCode")
    var currency:String,
    @SerializedName("ReceivableInterestRate")
    var interestRate:Double,
    @SerializedName("DebtInterestRate")
    var debtInterestRate:Double,
    @SerializedName("AmountOfBalance")
    var balance:Double,
    @SerializedName("AmountOfBalanceGross")
    var grossBalance:Double,
    @SerializedName("AvailableCaptainBalance")
    var captainBalance:Double,
    @SerializedName("AvailableBalance")
    var availableBalance:Double,
    @SerializedName("AccountName")
    var accountName:String,
    @SerializedName("IBANNo")
    var iban:String,
    @SerializedName("HasSchoolPayment")
    var isSchoolPaymentComing:Boolean,
    @SerializedName("AvailableCreditDeposit")
    var creditDeposit:Double
)
