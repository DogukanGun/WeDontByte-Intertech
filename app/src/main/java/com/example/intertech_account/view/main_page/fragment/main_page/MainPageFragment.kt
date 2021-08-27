package com.example.intertech_account.view.main_page.fragment.main_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentMainPageBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.model.api_model.get_corporate_account_transaction_list.GetCorporateAccountTransactionListModel
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view.main_page.fragment.account.adapter.AccountsInformationFragmentAdapter
import com.example.intertech_account.view.main_page.fragment.main_page.adapter.MainPageAdapter
import com.example.intertech_account.view_model.GetAccountViewModel
import com.example.intertech_account.view_model.GetCorporateAccountTransactionViewModel


class MainPageFragment : Fragment() {

    private var destinationAccountTitles = mutableListOf<String>()
    private var transactionNames = mutableListOf<String>()
    private var amounts = mutableListOf<String>()
    private var times = mutableListOf<String>()
    private var dates = mutableListOf<String>()

    private lateinit var binding:FragmentMainPageBinding
    private val getAccountViewModel: GetAccountViewModel by viewModels()
    private lateinit var getAccountModel: GetAccountModel

    private val getCorporateAccountTransactionViewModel: GetCorporateAccountTransactionViewModel by viewModels()
    private lateinit var getCorporateAccountTransactionListModel: GetCorporateAccountTransactionListModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_main_page,container,false)
        Constant.currentBottomMenu=0
        createAccountInformation()
        createRecyclerView()
        setHasOptionsMenu(true)
        Constant.isUserInformationTopBarButtonClick.observe(viewLifecycleOwner,{
            if (it==1 && Constant.currentBottomMenu==0){
                Constant.isUserInformationTopBarButtonClick.value=2
                val action = MainPageFragmentDirections.actionMainPageFragmentToUserInformationFragment()
                Constant.navHostFragment.findNavController().navigate(action)
            }
        })

        return binding.root
    }

    private fun createAccountInformation(){
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

        })
    }
    private fun createRecyclerView(){
        val recyclerView = binding.transactions
        recyclerView.layoutManager =  LinearLayoutManager(activity)
        recyclerView.adapter = MainPageAdapter()
        getCorporateAccountTransactionViewModel.apiRequest()
        getCorporateAccountTransactionViewModel.getCorporateAccountTransactionResult.observe(viewLifecycleOwner,{
            getCorporateAccountTransactionListModel=it
            if (getCorporateAccountTransactionListModel != null){
                val recyclerView = binding.transactions
                recyclerView.layoutManager =  LinearLayoutManager(activity)
                var adapter = recyclerView.adapter as MainPageAdapter
//                adapter.addList(getCorporateAccountTransactionListModel.getCorporateAccountTransactionListData.getCorporateAccountTransactionList[0].transactions)
                val dividerItemDecoration = DividerItemDecoration(
                    recyclerView.context,1
                )
                recyclerView.addItemDecoration(dividerItemDecoration)
            }


        })
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