package com.example.intertech_account.view_model


import androidx.lifecycle.ViewModel
import com.example.intertech_account.model.api_model.get_account.GetAccountBodyModel
import com.example.intertech_account.model.api_model.get_account.GetAccountHeaders
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.model.api_model.get_account.GetAccountParameters
import com.example.intertech_account.resources.api.ApiClient
import com.example.intertech_account.resources.api.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAccountViewModel : ViewModel(){
    private lateinit var apiService: ApiInterface
    private lateinit var getAccountList: GetAccountModel

    fun getAccountList():GetAccountModel{
        apiRequest()
        return getAccountList
    }
    private fun apiRequest(){
        val getAccountHeader: GetAccountHeaders = GetAccountHeaders("c1c2a508fdf64c14a7b44edc9241c9cd",
            "API","331eb5f529c74df2b800926b5f34b874","5252012362481156055")
        val getAccountParameter: GetAccountParameters = GetAccountParameters("13188998")
        var getAccountParameterList=Array(1){getAccountParameter}

        val getAccountGetAccountBodyModel: GetAccountBodyModel =
            GetAccountBodyModel(getAccountHeader,getAccountParameterList)

        ApiClient.getClient().getAccounts(getAccountGetAccountBodyModel).enqueue(object :
            Callback<GetAccountModel> {


            override fun onFailure(call: Call<GetAccountModel>, t: Throwable) {
                print("hehehehe"+t.localizedMessage)
            }
            override fun onResponse(call: Call<GetAccountModel>, response: Response<GetAccountModel>) {
                if (response.body() != null){
                    getAccountList=response.body()!!

                }
            }

        })
    }
}