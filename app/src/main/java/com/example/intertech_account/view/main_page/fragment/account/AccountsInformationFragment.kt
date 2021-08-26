package com.example.intertech_account.view.main_page.fragment.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentAccountsInformationBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.view_model.GetAccountViewModel


class AccountsInformationFragment : Fragment() {
    private val getAccountViewModel: GetAccountViewModel by viewModels()
    lateinit var getAccountModel: GetAccountModel
    var isFragmentUsedByViewPager=false
    var positionOfCurrentSpinneer=0
    lateinit var binding:FragmentAccountsInformationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountsInformationBinding.inflate(layoutInflater)
        if (isFragmentUsedByViewPager){
            updateLabel(0)
            var spinnerList:ArrayList<String> = arrayListOf<String>()
            for(index in getAccountModel.getAccountData.getAccountList){
                spinnerList.add(index.iban)
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                spinnerList
            )
            binding.accountName.adapter=adapter
            isFragmentUsedByViewPager=false
            binding.accountName.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                        updateLabel(position)


                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // your code here
                }
            })
        }



        return binding.root
    }

    fun updateLabel(position:Int){
        binding.accountType.text = getAccountModel.getAccountData.getAccountList[position].accountName
        binding.accountBalance.text = getAccountModel.getAccountData.getAccountList[position].availableBalance.toString()

    }

}