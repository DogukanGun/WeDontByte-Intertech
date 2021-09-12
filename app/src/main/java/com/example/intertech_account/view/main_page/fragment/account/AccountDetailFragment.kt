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
import com.google.gson.annotations.SerializedName

class AccountDetailFragment() : Fragment() {


    private lateinit var binding:FragmentAccountDetailBinding
    private var adapter = AccountDetailAdapter()
    private var adapter_ = AccountDetailAdapter()
    private lateinit var getAccountModel: GetAccountModel
    private var titles = arrayListOf<String>()
    private var values = arrayListOf<String>()
    val args: AccountDetailFragmentArgs by navArgs()
    private val getAccountViewModel:GetAccountViewModel by viewModels()
    private val titlesHashMap = hashMapOf<String,String>(
        "isBlocked" to "Hesap Bloke mi?",
        "branch"    to "Şube Adı",
        "isClosed"  to "Hesap Kapalı mı?",
        "currency" to "Döviz Kodu",
        "interestRate" to "Faiz Oranı",
//        "debtInterestRate" to "Borç Faiz Oranı",
        "balance" to "Bakiye",
//        "grossBalance" to "grossBalance?",
//        "captainBalance" to "captainBalance?",
//        "availableBalance" to "Kullanılabilir Bakiye?",
        "accountName" to "Hesap Adı",
        "iban" to "IBAN",
//        "isSchoolPaymentComing" to "isSchoolPaymentComing?",
//        "creditDeposit" to "creditDeposit?",
        "balanceAsTRY" to "TRY cinsinden bakiye",
        "closingDate" to "Hesap Kapanış tarihi",
        "openingDate" to "Hesap açılış Tarihi",
        "customerNo" to "Müşteri Numarası",
    )
    private val roles = hashMapOf<String,Int>(
        "Ad" to 0,
        "Soyad" to 1,
        "Müşteri Numarası" to 2,
        "Hesap Adı" to 3,
        "Şube Adı" to 4,
        "Bakiye" to 5,
        "TRY cinsinden bakiye" to 6,
        "IBAN" to 7,
        "Döviz Kodu" to 8,
        "Hesap Türü" to 9,
        "Faiz Oranı" to 10,
        "Hesap açılış Tarihi" to 11,
        "Hesap Kapanış tarihi" to 12,
        "Hesap Bloke mi?" to 13,
        "Hesap Kapalı mı?" to 14,


    )
    private val currencySigns:HashMap<String,String> = hashMapOf(
        "TRY" to "₺",
        "USD" to "$",
        "EUR" to "€",
        "GBP" to "£",
        "CAD" to "$"
    )
    var currency = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding= FragmentAccountDetailBinding.inflate(layoutInflater)
        var rawComing = arrayListOf<String>()

        adapter = AccountDetailAdapter()
        binding.recyclerview.adapter=adapter
        binding.recyclerview.layoutManager=LinearLayoutManager(activity)
        Log.d("Info",args.accountDetailFragmentListValues.toString())
        if(!args.accountDetailFragmentListValues.isNullOrEmpty()){
            Log.d("Info",args.accountDetailFragmentListValues.toString())
            currency = args.accountDetailFragmentListValues!!.substringAfter("currency!").substringBefore("?")
            Log.d("Info","Currency = ${currency}")
            rawComing.addAll(args.accountDetailFragmentListValues!!.split("?"))
            rawComing.removeAt(rawComing.size-1)
            for(i in 0..rawComing.size-1){
                if(rawComing[i].substringBefore("!").equals("name")){
                    titles.add(0, "Ad")
                    values.add(0, rawComing[i].substringAfter("!").substringBefore(" "))
                    titles.add(1, "Soyad")
                    values.add(1, rawComing[i].substringAfter("!").substringAfter(" "))
                    continue
                }
                val title = rawComing[i].substringBefore("!")
                var value = rawComing[i].substringAfter("!")
                if(value.equals("false"))value = "Hayır"
                else if(value.equals("true"))value="Evet"
                if(titlesHashMap[title].isNullOrEmpty()){
                    continue
                }

                if(title.contains("AsTRY") && currency.equals("TRY")){
                    continue
                }
                if(title.equals("openingDate")){
                    titlesHashMap[title]?.let { titles.add(it) }
                    values.add("13 Ekim 2019")
                    continue
                }
                if(title.equals("closingDate")){
                    titlesHashMap[title]?.let { titles.add(it) }
                    values.add("29 Kasım 2022")
                    continue
                }

                if(title.contains("alanc")){
                    titlesHashMap[title]?.let { titles.add(it) }
                    values.add(doubleAmount(value))
                }
                else if(title.contains("ate")){
                    titlesHashMap[title]?.let { titles.add(it) }
                    values.add(rate(value))
                }
                else{
                    titlesHashMap[title]?.let { titles.add(it) }
                    values.add(value)
                }

            }
            if(values.get(titles.indexOf("Faiz Oranı")).equals("% 0.0")){
                titles.add("Hesap Türü")
                values.add("Vadesiz")
            }
            else{
                titles.add("Hesap Türü")
                values.add("Vadeli")
            }

            rearrange()
            adapter_= (binding.recyclerview.adapter as? AccountDetailAdapter)!!
            binding.recyclerview.adapter = adapter_
            adapter_.addAccount(titles,values)

        }

        return binding.root


    }
    private fun doubleAmount(str:String):String{
        return str+" "+currencySigns[currency]
    }

    private fun rate(str:String):String{
        return "% $str"
    }
    fun rearrange(){
        val AllAccountsArrayList: ArrayList<String> = arrayListOf()
        val values: ArrayList<String> = arrayListOf()
        AllAccountsArrayList.addAll(titles)
        val comparator = Comparator { o1: String, o2: String ->
                return@Comparator roles[o1]!! - roles[o2]!!
        }

        AllAccountsArrayList.sortWith(comparator)
        for(i in AllAccountsArrayList){
            values.add(this.values.get(titles.indexOf(i)))
        }
        this.values.clear()
        titles.clear()
        this.values.addAll(values)
        titles.addAll(AllAccountsArrayList)
    }



}
