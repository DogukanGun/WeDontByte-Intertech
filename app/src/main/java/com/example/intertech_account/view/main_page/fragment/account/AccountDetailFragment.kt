package com.example.intertech_account.view.main_page.fragment.account

import android.accounts.Account
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intertech_account.databinding.FragmentAccountDetailBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.model.api_model.main_page.landmark.Landmark
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view.main_page.fragment.account.adapter.AccountDetailAdapter
import com.example.intertech_account.view.main_page.fragment.account.adapter.AllAccountsAdapter
import com.example.intertech_account.view_model.GetAccountViewModel

class AccountDetailFragment() : Fragment() {


    private lateinit var binding:FragmentAccountDetailBinding
    private var adapter = AccountDetailAdapter()
    private var adapter_ = AccountDetailAdapter()
    private lateinit var getAccountModel: GetAccountModel
    val args: AccountDetailFragmentArgs by navArgs()
    private val getAccountViewModel:GetAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentAccountDetailBinding.inflate(layoutInflater)

        var titles = arrayListOf<String>()
        var values = arrayListOf<String>()

        adapter = AccountDetailAdapter()
        binding.recyclerview.adapter=adapter
        binding.recyclerview.layoutManager=LinearLayoutManager(activity)
        titles.add("Ad")
        titles.add("Soyad")
        titles.add("Şube adı")
        titles.add("Hesap Adı")
        titles.add("Bakiye")
        titles.add("TL cinsinden Bakiye")
        titles.add("IBAN")
        titles.add("Faiz oranı")
        titles.add("Hesap Bloke mi?")
        titles.add("Hesap Kapalı mı!")


        if(!args.accountDetailFragmentListValues.isNullOrEmpty()){
            Log.d("Info",args.accountDetailFragmentListValues.toString())
            values.addAll(args.accountDetailFragmentListValues!!.split("?"))



        adapter_= (binding.recyclerview.adapter as? AccountDetailAdapter)!!
        binding.recyclerview.adapter = adapter_
        adapter_.addAccount(titles,values)

    }

        return binding.root


    }



}
