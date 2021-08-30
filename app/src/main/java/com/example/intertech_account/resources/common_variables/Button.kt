package com.example.intertech_account.resources.common_variables

import androidx.lifecycle.MutableLiveData

object Button {
    //account information buttons
    var qrButtonPressed= MutableLiveData<QrOperation>(QrOperation(false,"",false))

    //toolbar buttons
    var isUserInformationTopBarButtonClick=MutableLiveData(0)

}

open class QrOperation(
    var qrButtonPressed:Boolean,
    var qrAccountIban:String,
    var intentToCamera:Boolean
)