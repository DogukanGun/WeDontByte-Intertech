package com.example.intertech_Customer.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intertech_account.R
import com.example.intertech_account.model.api_model.get_account.*
import com.example.intertech_account.model.api_model.get_customer.*
import com.example.intertech_account.resources.api.ApiClient
import com.example.intertech_account.resources.api.ApiInterface
import com.example.intertech_account.resources.common_variables.Constant
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

class GetCustomerViewModel  : ViewModel(){

    private lateinit var apiService: ApiInterface
    var errorMessage = MutableLiveData<String>()
    lateinit var getCustomerInfo:MutableLiveData<GetCustomerModel>
    var job: Job? = null
    var loading = MutableLiveData<Boolean>()


    init {
        loading= MutableLiveData(true)
        errorMessage=MutableLiveData("")
        getCustomerInfo = MutableLiveData(GetCustomerModel("", GetCustomerData("", "", "","","", GetMobilePhoneNumber("","",""))))
    }

    fun apiRequest(){
        Log.d("Info:","Hello")
        val getCustomerHeader: GetCustomerHeaders = GetCustomerHeaders("c1c2a508fdf64c14a7b44edc9241c9cd",
            "API","331eb5f529c74df2b800926b5f34b874","5252012362481156055")
        val getCustomerParameter: GetCustomerParameters = GetCustomerParameters("13188998")
        var getCustomerParameterList=Array(1){getCustomerParameter}

        val getCustomerGetCustomerBodyModel: GetCustomerBodyModel =
            GetCustomerBodyModel(getCustomerHeader,getCustomerParameterList)

        try {
            job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                val response = ApiClient.getClient().getCustomerInfo(getCustomerGetCustomerBodyModel)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getCustomerInfo.value=(response.body())
                        print(getCustomerInfo.value!!.type)
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
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }
}

/*private lateinit var apiService: ApiInterface
   var errorMessage = MutableLiveData<String>()
   lateinit var getCustomerInfo:MutableLiveData<GetCustomerModel>
   var job: Job? = null
   var loading = MutableLiveData<Boolean>()

   init {
       loading= MutableLiveData(true)
       errorMessage=MutableLiveData("")
       getCustomerInfo = MutableLiveData(GetCustomerModel("", GetCustomerData("","","",
           "","",GetMobilePhoneNumber("","",""))))

   }

   fun apiRequest(){
       val getCustomerHeader: GetCustomerHeaders = GetCustomerHeaders("c1c2a508fdf64c14a7b44edc9241c9cd",
           "API","331eb5f529c74df2b800926b5f34b874","5252012362481156055")
       val getCustomerParameter: GetCustomerParameters = GetCustomerParameters("13188998")
       var getCustomerParameterList=Array(1){getCustomerParameter}

       val getCustomerGetCustomerBodyModel: GetCustomerBodyModel =
           GetCustomerBodyModel(getCustomerHeader,getCustomerParameterList)

       job = CoroutineScope(Dispatchers.IO).launch {
           val response = ApiClient.getClient().getCustomerInfo(getCustomerGetCustomerBodyModel)
           withContext(Dispatchers.Main) {
               if (response.isSuccessful) {
                   getCustomerInfo.value=(response.body())
                   print(getCustomerInfo.value!!.type)
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
   val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
       println("Error: ${throwable.localizedMessage}")
   }

    */