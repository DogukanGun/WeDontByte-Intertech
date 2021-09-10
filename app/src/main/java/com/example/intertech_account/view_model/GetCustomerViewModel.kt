package com.example.intertech_Customer.view_model


import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intertech_account.R
import com.example.intertech_account.model.api_model.get_customer.*
import com.example.intertech_account.resources.api.ApiClient
import com.example.intertech_account.resources.api.ApiInterface
import com.example.intertech_account.resources.common_variables.Constant
import com.itextpdf.text.factories.RomanAlphabetFactory.getString
import kotlinx.coroutines.*

class GetCustomerViewModel  : ViewModel(){

    private lateinit var apiService: ApiInterface
    var errorMessage = MutableLiveData<String>()
    lateinit var getCustomerInfo:MutableLiveData<GetCustomerModel>
    var job: Job? = null
    var loading = MutableLiveData<Boolean>()
    //TODO hasmap eklenecek
    /*
    private var monthMap= hashMapOf<String,String>(
        "01" to getString(R.string.Jan),
        "02" to getString(R.string.Feb),
        "03" to getString(R.string.Mar),
        "04" to getString(R.string.Apr),
        "05" to getString(R.string.May),
        "06" to getString(R.string.Jun),
        "07" to getString(R.string.Jul),
        "08" to getString(R.string.Aug),
        "09" to getString(R.string.Sep),
        "10" to getString(R.string.Oct),
        "11" to getString(R.string.Nov),
        "12" to getString(R.string.Dec)
    )

     */


    init {
        loading= MutableLiveData(true)
        errorMessage=MutableLiveData("")
        getCustomerInfo = MutableLiveData(GetCustomerModel("", GetCustomerData("", "", "","","", "",GetMobilePhoneNumber("","",""))))
    }

    fun apiRequest(){
        Log.d("Info:","Hello")
        val getCustomerHeader: GetCustomerHeaders = GetCustomerHeaders("c1c2a508fdf64c14a7b44edc9241c9cd",
            "API","331eb5f529c74df2b800926b5f34b874","5252012362481156055")
        val getCustomerParameter: GetCustomerParameters = GetCustomerParameters("33")
        var getCustomerParameterList=Array(1){getCustomerParameter}

        val getCustomerGetCustomerBodyModel: GetCustomerBodyModel =
            GetCustomerBodyModel(getCustomerHeader,getCustomerParameterList)

        try {
            job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                val response = ApiClient.getClient().getCustomerInfo(getCustomerGetCustomerBodyModel)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {

                        //BIRTH DATE STRING MANIPULATION
                        var year = response.body()!!.getCustomerData.birthDate.substring(0,4)
                        var month = response.body()!!.getCustomerData.birthDate.substring(5,7)
                        var day = response.body()!!.getCustomerData.birthDate.substring(8,10)
                        response.body()!!.getCustomerData.birthDate = "$day Nisan $year"

                        var countryCode = response.body()!!.getCustomerData.getMobilePhoneList.countryCode
                        var cityCode = response.body()!!.getCustomerData.getMobilePhoneList.cityCode
                        var number = response.body()!!.getCustomerData.getMobilePhoneList.number
                        response.body()!!.getCustomerData.getMobilePhoneList.number = "(+${countryCode}) ${cityCode} ${number.substring(0,3)} ${number.substring(3,5)} ${number.substring(5)}"

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