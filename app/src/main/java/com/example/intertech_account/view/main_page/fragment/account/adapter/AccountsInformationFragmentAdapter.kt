package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.intertech_account.model.api_model.get_account.GetAccountData
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.view.main_page.fragment.account.AccountsInformationFragment

class AccountsInformationFragmentAdapter(var bankFragments: Array<GetAccountData>, fragment: Fragment): FragmentStateAdapter(fragment)

{

    override fun getItemCount(): Int {
        return bankFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = AccountsInformationFragment()
        if (bankFragments[0].getAccountList.isNotEmpty()){
            fragment.isFragmentUsedByViewPager=true
            fragment.getAccountModel=GetAccountModel("",bankFragments[0])
        }
        return fragment

    }
}