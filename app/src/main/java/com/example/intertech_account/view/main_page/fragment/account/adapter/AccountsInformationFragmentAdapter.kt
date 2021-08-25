package com.example.intertech_account.view.main_page.fragment.account.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.intertech_account.model.api_model.get_account.GetAccountData
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.view.main_page.fragment.account.AccountsInformationFragment

class AccountsInformationFragmentAdapter(val bankFragments: ArrayList<GetAccountData>, activity: AppCompatActivity): FragmentStateAdapter(activity)

{
    private lateinit var fragments:Array<AccountsInformationFragment>
    init {

        for (bank in bankFragments){
            var newFragment=AccountsInformationFragment()
            newFragment.binding.accountBalance.text=bank.getAccountList[0].availableBalance.toString()
            for( iban in bank.getAccountList){

            }
        }
    }
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}