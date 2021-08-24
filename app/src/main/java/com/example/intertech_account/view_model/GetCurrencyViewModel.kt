package com.example.intertech_account.view_model

import androidx.lifecycle.ViewModel
import com.example.intertech_account.model.api_model.GetCurrencyModel
import com.example.intertech_account.model.api_model.get_account.GetAccountBodyModel
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.model.api_model.get_account.GetAccountParameters
import com.example.intertech_account.model.api_model.get_currency_body.GetCurrencyBodyModel
import com.example.intertech_account.model.api_model.get_currency_body.GetCurrencyHeader
 import com.example.intertech_account.resources.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetCurrencyViewModel():ViewModel() {
    //TODO bu kisima currency rate donulecek. Istenilen ceviri ismi verilecek
    //buradan donus olucak

    private lateinit var getCurrencyModel:GetCurrencyModel
    private lateinit var getCurrencyModelResult: GetCurrencyModel
    fun getCurrency(currency:String):Double{
        apiRequest()
        return searchInList(currency)
    }
    fun searchInList(currency: String):Double{
        var array = getCurrencyModelResult.dataGet.getCurrencyList
        var rate=array.filter { it.currencyCode==currency}
        if (rate.size!=0){
            return rate[0].changeRate
        }else{
            return -1.0
        }
    }
    fun apiRequest(){
        //TODO bu kisim gaye veya muhammet in katilimi ile doldurulacak
        val getCurrencyHeader: GetCurrencyHeader = GetCurrencyHeader("c1c2a508fdf64c14a7b44edc9241c9cd",
            "API","331eb5f529c74df2b800926b5f34b874","5252012362481156055",
        "string","string")
        val getCurrencyGetAccountBodyModel: GetCurrencyBodyModel =
            GetCurrencyBodyModel(getCurrencyHeader)

        ApiClient.getClient().getCurrencyRateList(getCurrencyGetAccountBodyModel).enqueue(object :
            Callback<GetCurrencyModel> {


            override fun onFailure(call: Call<GetCurrencyModel>, t: Throwable) {
                print("hehehehe"+t.localizedMessage)
            }
            override fun onResponse(call: Call<GetCurrencyModel>, response: Response<GetCurrencyModel>) {
                if (response.body() != null){
                    getCurrencyModelResult=response.body()!!

                }
            }

        })
    }

}