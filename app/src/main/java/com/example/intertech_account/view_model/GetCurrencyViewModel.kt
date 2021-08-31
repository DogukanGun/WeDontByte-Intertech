package com.example.intertech_account.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intertech_account.R
import com.example.intertech_account.model.api_model.GetCurrencyModel
import com.example.intertech_account.model.api_model.get_currency_body.GetCurrencyBodyModel
import com.example.intertech_account.model.api_model.get_currency_body.GetCurrencyHeader
 import com.example.intertech_account.resources.api.ApiClient
import com.example.intertech_account.resources.common_variables.Constant
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetCurrencyViewModel():ViewModel() {
    lateinit var currency:String
    val errorMessage = MutableLiveData<String>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()
    private lateinit var getCurrencyModelResult: MutableLiveData<GetCurrencyModel>

    private fun searchInList():Double{
        var array = getCurrencyModelResult.value!!.dataGet.getCurrencyList
        var rate=array.filter { it.currencyCode==currency}
        if (rate.size!=0){
            return rate[0].changeRate
        }else{
            return -1.0
        }
    }
    fun apiRequest(){
         val getCurrencyHeader: GetCurrencyHeader = GetCurrencyHeader("c1c2a508fdf64c14a7b44edc9241c9cd",
            "API","331eb5f529c74df2b800926b5f34b874","5252012362481156055",
        "string","string")
        val getCurrencyGetAccountBodyModel: GetCurrencyBodyModel =
            GetCurrencyBodyModel(getCurrencyHeader)

        try {
            job = CoroutineScope(Dispatchers.IO).launch {
                val response = ApiClient.getClient().getCurrencyRateList(getCurrencyGetAccountBodyModel)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getCurrencyModelResult.value=(response.body())
                        loading.value = false
                        searchInList()
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

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

//ApiClient.getClient().getCurrencyRateList(getCurrencyGetAccountBodyModel).enqueue(object :
//    Callback<GetCurrencyModel> {
//
//
//    override fun onFailure(call: Call<GetCurrencyModel>, t: Throwable) {
//        print("hehehehe"+t.localizedMessage)
//    }
//    override fun onResponse(call: Call<GetCurrencyModel>, response: Response<GetCurrencyModel>) {
//        if (response.body() != null){
//            getCurrencyModelResult=response.body()!!
//
//        }
//    }
//
//})