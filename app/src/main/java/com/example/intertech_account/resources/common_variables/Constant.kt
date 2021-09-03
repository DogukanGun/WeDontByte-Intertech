package com.example.intertech_account.resources.common_variables

import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import com.example.intertech_account.model.api_model.GetCurrency
import com.example.intertech_account.model.api_model.get_account.GetAccountHeaders

// Diğer classların ortak kullandığı constantlar

object Constant{
        var BASE_URL: String = "https://api-gateway.intertech.com.tr/BankingApiV01/"
        var GET_ACCOUNTS_HEADER= GetAccountHeaders("c1c2a508fdf64c14a7b44edc9241c9cd",
                "API","773f2d49-cad6-45a4-a568-439e417a61f9",
                "773f2d49-cad6-45a4-a568-439e417a61f9")
        lateinit var navHostFragment: NavHostFragment
        var currentBottomMenu=0
        var exceptionForApp=MutableLiveData("")
        lateinit var currencyList:ArrayList<GetCurrency>
        var languageStatus=0
}

