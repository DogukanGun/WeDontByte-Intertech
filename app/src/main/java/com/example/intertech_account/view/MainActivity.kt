package com.example.intertech_account.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivityMainBinding
import com.example.intertech_account.model.api_model.get_accounts_body.GetAccountModel
import com.example.intertech_account.model.api_model.get_accounts_body.GetAccountBody
import com.example.intertech_account.model.api_model.get_accounts_body.GetAccountHeader
import com.example.intertech_account.model.api_model.get_accounts_body.GetAccountParameter
import com.example.intertech_account.resources.api.ApiClient
import com.example.intertech_account.resources.api.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var apiService: ApiInterface
    lateinit var getAccountList: GetAccountModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        NavigationUI.setupWithNavController(binding.bottomNavigationView,navHostFragment.navController)

        val getAccountHeader:GetAccountHeader= GetAccountHeader("c1c2a508fdf64c14a7b44edc9241c9cd",
        "API","331eb5f529c74df2b800926b5f34b874","5252012362481156055")
        val getAccountParameter:GetAccountParameter= GetAccountParameter("13188998")
        var getAccountParameterList=Array(1){getAccountParameter}

        val getAccountGetAccountBody:GetAccountBody=GetAccountBody(getAccountHeader,getAccountParameterList)

        ApiClient.getClient().getAccounts(getAccountGetAccountBody).enqueue(object : Callback<GetAccountModel>{


            override fun onFailure(call: Call<GetAccountModel>, t: Throwable) {
                print("hehehehe"+t.localizedMessage)
            }



            override fun onResponse(call: Call<GetAccountModel>, response: Response<GetAccountModel>) {
                if (response.body() != null){
                    getAccountList=response.body()!!
                }            }

        })
    }
}