package com.example.intertech_account.resources.common_variables

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.intertech_account.R
import com.example.intertech_account.model.api_model.get_account.GetAccountHeaders
import com.example.intertech_account.view.main_page.fragment.main_page.MainPageFragmentDirections

object Constant{
        var BASE_URL: String = "https://api-gateway.intertech.com.tr/BankingApiV01/"
        var GET_ACCOUNTS_HEADER= GetAccountHeaders("c1c2a508fdf64c14a7b44edc9241c9cd",
                "API","773f2d49-cad6-45a4-a568-439e417a61f9",
                "773f2d49-cad6-45a4-a568-439e417a61f9")
        var isUserInformationTopBarButtonClick=MutableLiveData(0)
        lateinit var navHostFragment: NavHostFragment
        var currentBottomMenu=0

}

