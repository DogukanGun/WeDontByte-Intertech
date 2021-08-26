package com.example.intertech_account.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intertech_account.model.api_model.GetCurrencyModel
import com.example.intertech_account.model.api_model.get_account.GetAccountData
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.model.api_model.get_corporate_account_transaction_list.GetCorporateAccountTransactionListBodyModel
import com.example.intertech_account.model.api_model.get_corporate_account_transaction_list.GetCorporateAccountTransactionListHeader
import com.example.intertech_account.model.api_model.get_corporate_account_transaction_list.GetCorporateAccountTransactionListModel
import com.example.intertech_account.model.api_model.get_corporate_account_transaction_list.GetCorporateAccountTransactionListParameter
import com.example.intertech_account.model.api_model.get_currency_body.GetCurrencyBodyModel
import com.example.intertech_account.model.api_model.get_currency_body.GetCurrencyHeader
import com.example.intertech_account.resources.api.ApiClient
import kotlinx.coroutines.*

class GetCorporateAccountTransactionViewModel:ViewModel() {
    val errorMessage = MutableLiveData<String>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()
    var getCorporateAccountTransactionResult: MutableLiveData<GetCorporateAccountTransactionListModel>

    init {
        getCorporateAccountTransactionResult= MutableLiveData()
    }
    fun apiRequest(){
        val getCorporateAccountTransactionHeader: GetCorporateAccountTransactionListHeader = GetCorporateAccountTransactionListHeader("c1c2a508fdf64c14a7b44edc9241c9cd",
            "API","773f2d49-cad6-45a4-a568-439e417a61f9","773f2d49-cad6-45a4-a568-439e417a61f9",
            "string","string")
        val getCorporateAccountTransactionParameter=GetCorporateAccountTransactionListParameter("12695508",4420,352,"","","2020-06-18T13:40:00+03:00","2020-06-23T13:40:00+03:00")
        val getCorporateAccountTransactionListBodyModel  = GetCorporateAccountTransactionListBodyModel(getCorporateAccountTransactionHeader,
                arrayOf(getCorporateAccountTransactionParameter))

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.getClient().getCorporateAccountTransactionList(getCorporateAccountTransactionListBodyModel)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    getCorporateAccountTransactionResult.value=(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }
    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}