package com.example.intertech_account.view_model


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intertech_account.model.api_model.get_account.*
import com.example.intertech_account.resources.api.ApiClient
import com.example.intertech_account.resources.api.ApiInterface
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        job = CoroutineScope(Dispatchers.IO).launch {
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

//ApiClient.getClient().getAccounts(getAccountGetAccountBodyModel).enqueue(object :
//    Callback<GetAccountModel> {
//
//
//    override fun onFailure(call: Call<GetAccountModel>, t: Throwable) {
//        print("hehehehe" + t.localizedMessage)
//    }
//
//    override fun onResponse(
//        call: Call<GetAccountModel>,
//        response: Response<GetAccountModel>
//    ) {
//        if (response.body() != null) {
//            getAccountList.value = response.body()!!
//
//        }
//    }
//
//})