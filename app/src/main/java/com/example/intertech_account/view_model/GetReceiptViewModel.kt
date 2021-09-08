package com.example.intertech_account.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intertech_account.R
import com.example.intertech_account.model.api_model.get_receipt.*
import com.example.intertech_account.resources.api.ApiClient
import com.example.intertech_account.resources.api.ApiInterface
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.resources.common_variables.Receipt
import kotlinx.coroutines.*

class GetReceiptViewModel:ViewModel() {
    private lateinit var apiService: ApiInterface
    var errorMessage = MutableLiveData<String>()
    lateinit var getContentOfReceipt: MutableLiveData<GetContent>
    var job: Job? = null
    var loading = MutableLiveData<Boolean>()


    init {
        loading= MutableLiveData(true)
        errorMessage= MutableLiveData("")
        getContentOfReceipt = MutableLiveData(
            GetContent("","")
        )

    }

    fun apiRequest(){
        Log.d("Info:","Hello")
        val getReceiptHeader: GetReceiptHeader = GetReceiptHeader("API7909c7de460b462aa1d",
            "API","jBGsMIxIdRTjniCBMnnc","fdf19b63-472e-4eee-925e-518df3b2f5b9")
        val getReceiptParameter: GetReceiptParameters = GetReceiptParameters(Receipt.branchCode,Receipt.transactionDate,Receipt.referenceNo,Receipt.customerNo,Receipt.isPdf.toString())
        var getReceiptParameterList=Array(1){getReceiptParameter}

        val getReceiptBodyModel: GetReceiptBodyModel =
            GetReceiptBodyModel(getReceiptHeader,getReceiptParameterList)

        try {
            job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                val response = ApiClient.getClient().getReceipt(getReceiptBodyModel)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getContentOfReceipt.value=(response.body()!!.getReceiptData.content)
                        loading.value = false
                    } else {
                                        onError("Error : ${response.message()} ")
                    }
                }
            }
        }catch (e:Exception) {
            Constant.exceptionForApp.value = R.string.internet_exception.toString()
        }


    }
    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }
}