package com.example.intertech_account.model.api_model.get_receipt

import com.google.gson.annotations.SerializedName

// Requestin parametre detayları
data class GetReceiptParameters (
    @SerializedName("BranchCode")
    var branchCode: Int,
    @SerializedName("TransactionDate")
    var transactionDate: String,
    @SerializedName("ReferenceNo")
    var referenceNo: Int,
    @SerializedName("CustomerNo")
    var customerNo: Int,
    @SerializedName("IsPdf")
    var isPdf: String // Bu boolean da olabilir gibi ama postmande string gönderince de 200 OK döndü
                        //- Ece K 02.09.2021 18.57
)