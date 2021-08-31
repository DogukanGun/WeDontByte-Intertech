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
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionList
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionListModel
 import com.example.intertech_account.resources.common_variables.Button
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view.main_page.fragment.account.adapter.AccountsInformationFragmentAdapter
import com.example.intertech_account.view.main_page.fragment.main_page.adapter.MainPageAdapter
import com.example.intertech_account.view_model.GetAccountTransactionViewModel
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

    private val getAccountTransactionViewModel: GetAccountTransactionViewModel by viewModels()
    private lateinit var getAccountTransactionListModel: GetAccountTransactionListModel


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
        listenAccountsInformationFragmentButtons()
        setHasOptionsMenu(true)
        Button.isUserInformationTopBarButtonClick.observe(viewLifecycleOwner,{
            if (it==1 && Constant.currentBottomMenu==0){
                Button.isUserInformationTopBarButtonClick.value=2
                val action = MainPageFragmentDirections.actionMainPageFragmentToUserInformationFragment()
                Constant.navHostFragment.findNavController().navigate(action)
            }
        })

        return binding.root
    }

    private fun listenAccountsInformationFragmentButtons(){
        Button.qrButtonPressed.observe(viewLifecycleOwner,{
            if (it.qrButtonPressed){
                Button.qrButtonPressed.value!!.qrButtonPressed=false
                val action = MainPageFragmentDirections.actionMainPageFragmentToQRCodeOptionSelectFragment(Button.qrButtonPressed.value!!.qrAccountIban)
                Constant.navHostFragment.findNavController().navigate(action)
            }
        })
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
        getAccountTransactionViewModel.apiRequest()
        getAccountTransactionViewModel.getAccountTransactionResult.observe(viewLifecycleOwner,{
            getAccountTransactionListModel=it
            if (getAccountTransactionListModel.data.activityCollection.isNotEmpty()){
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
}