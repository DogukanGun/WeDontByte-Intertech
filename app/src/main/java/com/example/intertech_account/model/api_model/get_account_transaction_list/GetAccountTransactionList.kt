package com.example.intertech_account.model.api_model.get_account_transaction_list

import com.google.gson.annotations.SerializedName

// Respondun data kısmındaki activity collectionın detayları
data class GetAccountTransactionList(
    @SerializedName("\$type")
    var type: String,
    @SerializedName("Date")
    var date: String,
    @SerializedName("DueDate")
    var dueDate: String,
    @SerializedName("Explanation")
    var explanation: String,
    @SerializedName("Amount")
    var amount: Double,
    @SerializedName("RemainingBalance")
    var remainingBalance: Double,
    @SerializedName("TransactionReference")
    var transactionReference: String,
    @SerializedName("TransactionCode")
    var transactionCode: String,
    @SerializedName("TransactionBranchCode")
    var transactionBranchCode: String,
    @SerializedName("ChannelCode")
    var channelCode: String,
    @SerializedName("Time")
    var time: String,
    @SerializedName("UserCode")
    var userCode: String,
    @SerializedName("GroupRef")
    var groupRef: String,
    @SerializedName("AvailableBalance")
    var availableBalance: Double,
    @SerializedName("ReferenceNumber")
    var referenceNumber: String
)
