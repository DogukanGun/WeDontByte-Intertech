package com.example.intertech_account.view.main_page.fragment.main_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentMainPageBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountData
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.view.main_page.fragment.account.adapter.AccountsInformationFragmentAdapter
import com.example.intertech_account.view.main_page.fragment.account.adapter.AllAccountsAdapter
import com.example.intertech_account.view.main_page.fragment.main_page.adapter.MainPageAdapter
import com.example.intertech_account.view_model.GetAccountViewModel


class MainPageFragment : Fragment() {

    private var destinationAccountTitles = mutableListOf<String>()
    private var transactionNames = mutableListOf<String>()
    private var amounts = mutableListOf<String>()
    private var times = mutableListOf<String>()
    private var dates = mutableListOf<String>()

    private lateinit var binding:FragmentMainPageBinding
    private val getAccountViewModel: GetAccountViewModel by viewModels()
    private lateinit var getAccountModel: GetAccountModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_main_page,container,false)
        binding.accountsInformation.adapter=AccountsInformationFragmentAdapter(emptyArray(),
            this
        )
        getAccountViewModel.apiRequest()
        getAccountViewModel.getAccountList.observe(viewLifecycleOwner,{
            getAccountModel=it
            var adapter_=AccountsInformationFragmentAdapter(
                arrayOf(getAccountModel.getAccountData),
                this
            )
            binding.accountsInformation.adapter=adapter_
            val recyclerView = binding.transactions
            recyclerView.layoutManager =  LinearLayoutManager(activity)
            recyclerView.adapter = MainPageAdapter(destinationAccountTitles,transactionNames,amounts,times,dates)
            val dividerItemDecoration = DividerItemDecoration(
                recyclerView.context,1
            )
            recyclerView.addItemDecoration(dividerItemDecoration)
            updateRecyclerView()

        })
        return binding.root
    }
    private fun addToRecyclerView(destinationAccountTitle: String, transactionName: String, amount: String, time: String, date: String)
    {
        destinationAccountTitles.add(destinationAccountTitle)
        transactionNames.add(transactionName)
        amounts.add(amount)
        times.add(time)
        dates.add(date)
    }
    private fun updateRecyclerView(_wannaWipeData: Boolean = true)
    {
        if(_wannaWipeData)
        {
            destinationAccountTitles.clear()
            transactionNames.clear()
            amounts.clear()
            times.clear()
            dates.clear()
        }


        for(i in 1..25)
        {
            addToRecyclerView("Dest. Account $i", "Transaction $i", "\$$i", "$i:$i", "$i/$i/$i")
        }

    }


}