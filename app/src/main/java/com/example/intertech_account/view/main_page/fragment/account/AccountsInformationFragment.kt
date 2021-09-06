package com.example.intertech_account.view.main_page.fragment.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.intertech_account.databinding.FragmentAccountsInformationBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionList
import com.example.intertech_account.resources.common_variables.Button
import com.example.intertech_account.resources.common_variables.QrOperation
import com.example.intertech_account.view_model.GetAccountViewModel


class AccountsInformationFragment : Fragment() {
    private val getAccountViewModel: GetAccountViewModel by viewModels()
    lateinit var getAccountModel: GetAccountModel
    private lateinit var currentIban:String
    var isFragmentUsedByViewPager=false
    lateinit var binding:FragmentAccountsInformationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountsInformationBinding.inflate(layoutInflater)
        if (isFragmentUsedByViewPager){
            updateLabel(0)
            val spinnerList:ArrayList<String> = arrayListOf<String>()

            //Bütün hesapların eklenmesi
            for(index in getAccountModel.getAccountData.getAccountList){
                spinnerList.add(index.accountName + " / " + index.balance + " " + index.currency)
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                spinnerList
            )
            binding.accountName.adapter=adapter
            binding.qrButton.setOnClickListener {
                Button.qrButtonPressed.value=QrOperation(true,currentIban,false)
            }
            isFragmentUsedByViewPager=false
            binding.accountName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
            }
        }

        return binding.root
    }

    //Hesap sayfasının bilgi güncellemesi

    fun updateLabel(position:Int){
        binding.subeText.text = getAccountModel.getAccountData.getAccountList[position].branch
        binding.accountType.text = getAccountModel.getAccountData.getAccountList[position].iban
        binding.accountBalance.text = (getAccountModel.getAccountData.getAccountList[position].availableBalance+1500.0).toString() + " " + getAccountModel.getAccountData.getAccountList[position].currency.toString()
        currentIban = getAccountModel.getAccountData.getAccountList[position].iban
        if(getAccountModel.getAccountData.getAccountList[position].interestRate == 0.0) {
            binding.vadeliText.text ="Vadesiz ${getAccountModel.getAccountData.getAccountList[position].currency.toString()} Hesabım"
        }
        else{
            binding.vadeliText.text ="Vadeli ${getAccountModel.getAccountData.getAccountList[position].currency.toString()} Hesabım"
        }
    }
    private fun createDummyTransactionList(size:Int): Array<GetAccountTransactionList> {
        var x = ArrayList<GetAccountTransactionList>()
        for (i in 0..size){

            x.add(GetAccountTransactionList("test","test","24314","234234","test","test",(-150..150).random().toDouble(),122.2,
                "t","t","t","t","t","t","t",
                233.3,"t"))
        }


        return x.toTypedArray()
    }

}