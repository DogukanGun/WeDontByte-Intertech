package com.example.intertech_account.model.api_model.get_corporate_account_transaction_list

import com.google.gson.annotations.SerializedName

data class GetCorporateAccountTranscationListTransactions(
    @SerializedName("\$type")
    var type:String,
    @SerializedName("ReceiptNumber")
    var receiptNumber:String,
    @SerializedName("DestinationAccountTitle")
    var destinationAccountTitle:String,
    @SerializedName("TransactionName")
    var transactionName:String,
    @SerializedName("TransactionDetail1")
    var transactionDetail1:String,
    @SerializedName("TransactionDetail2")
    var transactionDetail2:String,
    @SerializedName("TransactionDetail3")
    var transactionDetail3:String,
    @SerializedName("TransactionDetail4")
    var transactionDetail4:String,
    @SerializedName("TransactionDetail5")
    var transactionDetail5:String,
    @SerializedName("BankDescription")
    var bankDescription:String,
    @SerializedName("BalanceBeforeTransaction")
    var balanceBeforeTransaction:Double,
    @SerializedName("BalanceAfterTransaction")
    var balanceAfterTransaction:Double,
    @SerializedName("Amount")
    var amount:Double,
    @SerializedName("Time")
    var time:Double,
    @SerializedName("Date")
    var date:String,
    @SerializedName("IslemDurumu")
    var status:String,
    @SerializedName("DebitCreditType")
    var debitCreditType:String
) {

}