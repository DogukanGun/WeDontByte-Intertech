package com.example.intertech_account.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivityMainBinding
import com.example.intertech_account.model.api_model.AccountModel
import com.example.intertech_account.model.api_model.get_accounts_body.BodyModel
import com.example.intertech_account.model.api_model.get_accounts_body.Headers
import com.example.intertech_account.model.api_model.get_accounts_body.Parameters
import com.example.intertech_account.resources.api.ApiClient
import com.example.intertech_account.resources.api.ApiInterface
import okhttp3.internal.http2.Header
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var apiService: ApiInterface
    lateinit var accountList: AccountModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        NavigationUI.setupWithNavController(binding.bottomNavigationView,navHostFragment.navController)

        val getAccountHeader:Headers= Headers("c1c2a508fdf64c14a7b44edc9241c9cd",
        "API","331eb5f529c74df2b800926b5f34b874","5252012362481156055")
        val getAccountParameter:Parameters= Parameters("13188998")
        var getAccountParameterList=Array(1){getAccountParameter}

        val getAccountBodyModel:BodyModel=BodyModel(getAccountHeader,getAccountParameterList)

        ApiClient.getClient().getAccounts(getAccountBodyModel).enqueue(object : Callback<AccountModel>{


            override fun onFailure(call: Call<AccountModel>, t: Throwable) {
                print("hehehehe"+t.localizedMessage)
            }



            override fun onResponse(call: Call<AccountModel>, response: Response<AccountModel>) {
                if (response.body() != null){
                    accountList=response.body()!!
                }            }

        })
    }
}