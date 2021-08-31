package com.example.intertech_account.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigator
import com.example.intertech_account.R
import com.example.intertech_account.model.api_model.get_account_transaction_list.*
import com.example.intertech_account.resources.api.ApiClient
import com.example.intertech_account.resources.common_variables.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named

class GetAccountTransactionViewModel:ViewModel() {


    private val errorMessage=MutableLiveData<String>("")
    var job: Job? = null
    private val loading = MutableLiveData<Boolean>(false)
    var getAccountTransactionResult: MutableLiveData<GetAccountTransactionListModel>

    init {
        getAccountTransactionResult= MutableLiveData()
    }
    fun apiRequest(){
        val getAccountTransactionHeader: GetAccountTransactionListHeader = GetAccountTransactionListHeader("c1c2a508fdf64c14a7b44edc9241c9cd",
            "API","773f2d49-cad6-45a4-a568-439e417a61f9","773f2d49-cad6-45a4-a568-439e417a61f9",
            "string","string")
        val getSourceAccount = GetSourceAccount("0","13191945","0")
        val getAccountTransactionParameter=GetAccountTransactionListParameter(getSourceAccount,"126955084","","")
        val getAccountTransactionListBodyModel  = GetAccountTransactionListBodyModel(getAccountTransactionHeader,
                arrayOf(getAccountTransactionParameter))

        try{
            job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                val response = ApiClient.getClient().getAccountTransactionList(getAccountTransactionListBodyModel)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getAccountTransactionResult.value=(response.body())
                        loading.value = false
                    } else {
                        onError("Error : ${response.message()} ")
                    }
                }
            }
        }catch (e:Exception){
            Constant.exceptionForApp.value= R.string.internet_exception.toString()
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
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }
}