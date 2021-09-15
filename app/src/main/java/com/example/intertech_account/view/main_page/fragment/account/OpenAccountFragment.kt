package com.example.intertech_account.view.main_page.fragment.account

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentOpenAccountBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.model.api_model.open_account.GetNewAccountRequest
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view.login_page.activity.UserLoginActivity
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view.main_page.fragment.account.adapter.AllAccountsAdapter
import com.example.intertech_account.view.main_page.fragment.main_page.MainPageFragment
import com.example.intertech_account.view_model.GetAccountViewModel
import com.example.intertech_account.view_model.GetOpenAccountViewModel
import com.itextpdf.awt.geom.misc.Messages.getString
import com.jaredrummler.materialspinner.MaterialSpinner

class OpenAccountFragment : Fragment() {

    private lateinit var binding: FragmentOpenAccountBinding
    private lateinit var accountName: String
    private lateinit var accountCurrencyCode:String
    private lateinit var newAccountIban:String
    private val getOpenAccountViewModel: GetOpenAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOpenAccountBinding.inflate(layoutInflater)
        (requireActivity() as MainActivity).binding.topAppBarToolbar.title=getString(R.string.app_title)

        val spinnerListForCurrency: ArrayList<String> = arrayListOf<String>()

        for(index in Constant.currencyList){
            spinnerListForCurrency.add(index.currencyCode)
        }
        spinnerListForCurrency.add("TRY")
        val spinnerListForAccountType: ArrayList<String> = arrayListOf<String>()

        spinnerListForAccountType.addAll(arrayListOf(getString(R.string.vadeli),getString(R.string.vadesiz)))
        val adapterForAccountType: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerListForAccountType
        )
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerListForCurrency
        )

        binding.accountTypeSpinner.setAdapter(adapterForAccountType)
        binding.accountTypeSpinner.setBackgroundResource(R.drawable.qr_radio_button_selected)
        binding.accountTypeSpinner.popupWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.mainpagespinnerpopupbakcground))
        binding.accountTypeSpinner.setTextColor(resources.getColor(R.color.intertech_textview_text_color))
        binding.accountTypeSpinner.setTextAppearance(R.style.myText)
        binding.accountTypeSpinner.gravity= Gravity.CENTER

        binding.currencySpinner.setAdapter(adapter)
        binding.currencySpinner.setBackgroundResource(R.drawable.qr_radio_button_selected)
        binding.currencySpinner.popupWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.mainpagespinnerpopupbakcground))
        binding.currencySpinner.setTextColor(resources.getColor(R.color.intertech_textview_text_color))
        binding.currencySpinner.setTextAppearance(R.style.myText)
        binding.currencySpinner.gravity= Gravity.CENTER
        binding.currencySpinner.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener<String> { view, position, id, item ->

            accountCurrencyCode = item
            Log.d("Info",accountCurrencyCode+" Seçildi!")

        })
        binding.currencySpinner.setDropdownHeight(375)

        binding.openAccountButton.setOnClickListener{


            if(binding.newAccountName.text.isNotEmpty()){
                accountName = binding.newAccountName.text.toString()
                Toast.makeText(activity as MainActivity, "Hesap oluşturuluyor...",Toast.LENGTH_LONG).show()

                var getNewAccountRequest= GetNewAccountRequest(accountName,"0","9190",accountCurrencyCode,"13146677")

                getOpenAccountViewModel.apiRequest(getNewAccountRequest)
                getOpenAccountViewModel.getOpenAccountList.observe(viewLifecycleOwner,{
                    if(!it.getOpenAccountData.getNewAccountResponse.iban.equals("")){
                        newAccountIban = it.getOpenAccountData.getNewAccountResponse.iban
                        accountName = it.getOpenAccountData.getNewAccountResponse.accountName

                        Toast.makeText(context,"Yeni hesap adınız:\n"+accountName + "\nYeni ibanınız:\n" + newAccountIban, Toast.LENGTH_LONG).show()

                    }

                })

            }

            else{

                Toast.makeText(activity as MainActivity,"Hesap adı giriniz!",Toast.LENGTH_LONG).show()
            }



        }




        return binding.root
    }

}