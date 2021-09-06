package com.example.intertech_account.resources.common_variables

import androidx.lifecycle.MutableLiveData

object Receipt {
    var branchCode:Int=9142
    var transactionDate:String="2021-01-28"
    var referenceNo:Int=3411
    var customerNo:Int =123
    val isPdf= true

    val isReceiptButtonClicked=MutableLiveData(false)
}