package com.example.intertech_account.view.main_page.fragment.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intertech_account.databinding.FragmentAllAccountsBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view.main_page.fragment.account.adapter.AllAccountsAdapter
import com.example.intertech_account.view.main_page.fragment.main_page.MainPageFragmentDirections
import com.example.intertech_account.view_model.GetAccountViewModel


class AllAccountsFragment : Fragment() {

    private lateinit var binding:FragmentAllAccountsBinding
    private val getAccountViewModel: GetAccountViewModel by viewModels()
    private lateinit var getAccountModel: GetAccountModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllAccountsBinding.inflate(layoutInflater)
        var adapter=AllAccountsAdapter(arrayListOf())

        binding.allAccounts.adapter=adapter
        binding.allAccounts.layoutManager=LinearLayoutManager(activity)

        getAccountViewModel.apiRequest()
        getAccountViewModel.getAccountList.observe(viewLifecycleOwner,{
                getAccountModel=it
                var adapter_=binding.allAccounts.adapter as? AllAccountsAdapter
                adapter_!!.addAccount(getAccountModel.getAccountData.getAccountList)

        })
        
        return binding.root

    }
    fun listenTopBarButton(){
            val action = MainPageFragmentDirections.actionMainPageFragmentToUserInformationFragment()
            Constant.navHostFragment.findNavController().navigate(action)
    }


}