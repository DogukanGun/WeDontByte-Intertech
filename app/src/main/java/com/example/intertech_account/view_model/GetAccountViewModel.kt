package com.example.intertech_account.view_model


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intertech_account.R
import com.example.intertech_account.model.api_model.get_account.*
import com.example.intertech_account.resources.api.ApiClient
import com.example.intertech_account.resources.api.ApiInterface
import com.example.intertech_account.resources.common_variables.Constant
import kotlinx.coroutines.*

class GetAccountViewModel : ViewModel(){
    private lateinit var apiService: ApiInterface
    var errorMessage = MutableLiveData<String>()
    var job: Job? = null
    var loading = MutableLiveData<Boolean>()
    lateinit var getAccountList: MutableLiveData<GetAccountModel>

    init {
        loading= MutableLiveData(true)
        errorMessage=MutableLiveData("")
        getAccountList= MutableLiveData(GetAccountModel("", GetAccountData("", emptyArray())))
    }
    fun apiRequest(){
        val getAccountHeader: GetAccountHeaders = GetAccountHeaders("c1c2a508fdf64c14a7b44edc9241c9cd",
            "API","331eb5f529c74df2b800926b5f34b874","5252012362481156055")
        val getAccountParameter: GetAccountParameters = GetAccountParameters("150")
        var getAccountParameterList=Array(1){getAccountParameter}

        val getAccountGetAccountBodyModel: GetAccountBodyModel =
            GetAccountBodyModel(getAccountHeader,getAccountParameterList)

        try{
            job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                val response = ApiClient.getClient().getAccounts(getAccountGetAccountBodyModel)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getAccountList.value=(response.body())
                        print(getAccountList.value!!.type)
                        loading.value = false
                    } else {
                        onError("Error : ${response.message()} ")
                    }
                }
            }
        }catch (e:Exception){
            Constant.exceptionForApp.value=R.string.internet_exception.toString()
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
        errorMessage.value="ApiError"
        Log.d("GetAccountViewModel", throwable.localizedMessage!!)

    }
}
