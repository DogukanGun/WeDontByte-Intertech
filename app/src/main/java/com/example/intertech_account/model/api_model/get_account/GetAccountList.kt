package com.example.intertech_account.model.api_model.get_account

// Respond datanın bilgileri

import com.google.gson.annotations.SerializedName

data class GetAccountList(
    @SerializedName("IsBlockedAccount")
    var isBlocked: Boolean,
    @SerializedName("BranchName")
    var branch: String,
    @SerializedName("ShortName")
    var name: String,
    @SerializedName("IsClosed")
    var isClosed: Boolean,
    @SerializedName("CurrencyCode")
    var currency: String,
    @SerializedName("ReceivableInterestRate")
    var interestRate: Double,
    @SerializedName("DebtInterestRate")
    var debtInterestRate: Double,
    @SerializedName("AmountOfBalance")
    var balance: Double,
    @SerializedName("AmountOfBalanceGross")
    var grossBalance: Double,
    @SerializedName("AvailableCaptainBalance")
    var captainBalance: Double,
    @SerializedName("AvailableBalance")
    var availableBalance: Double,
    @SerializedName("AccountName")
    var accountName: String,
    @SerializedName("IBANNo")
    var iban: String,
    @SerializedName("HasSchoolPayment")
    var isSchoolPaymentComing: Boolean,
    @SerializedName("AvailableCreditDeposit")
    var creditDeposit: Double,
    @SerializedName("CustomerNo")
    var cutomerNo: String,
    @SerializedName("AccountClosingDate")
    var closingDate: String,
    @SerializedName("AccountOpenningDate")
    var openingDate: String,
    @SerializedName("BlockageExplanation")
    var blockageExplanation: String,
    @SerializedName("BlockageAmount")
    var blockageAmount: String,

var balanceAsTRY:Double?
)