package com.example.intertech_account.model.api_model.get_corporate_account_transaction_list

import com.google.gson.annotations.SerializedName

data class GetCorporateAccountTransactionListBodyModel(
    @SerializedName("Header")
    var header: GetCorporateAccountTransactionListHeader,
    @SerializedName("Parameters")
    var parameters:Array<GetCorporateAccountTransactionListParameter>,

    ){

}
