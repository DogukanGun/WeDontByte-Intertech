package com.example.intertech_account.view.main_page.fragment.account

import android.accounts.Account
import android.content.res.Resources
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
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentAccountDetailBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.model.api_model.main_page.landmark.Landmark
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.resources.common_variables.Constant.amountFormatter
import com.example.intertech_account.resources.common_variables.fragment.AccountDetailFragmentCommonVariables
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view.main_page.fragment.account.adapter.AccountDetailAdapter
import com.example.intertech_account.view.main_page.fragment.account.adapter.AllAccountsAdapter
import com.example.intertech_account.view_model.GetAccountViewModel
import com.google.gson.annotations.SerializedName

class AccountDetailFragment : Fragment() {

    private lateinit var binding: FragmentAccountDetailBinding
    private var adapter = AccountDetailAdapter()
    private var adapter_ = AccountDetailAdapter()
     private var titles = arrayListOf<String>()
    private var values = arrayListOf<String>()
    private lateinit var titlesHashMap:HashMap<String,String>
    private lateinit var currencySigns:HashMap<String,String>
    private lateinit var roles :HashMap<String,Int>
    val args: AccountDetailFragmentArgs by navArgs()

    var currency = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentAccountDetailBinding.inflate(layoutInflater)
        currencySigns=AccountDetailFragmentCommonVariables.currencySigns
        roles = hashMapOf(

            resources.getString(
                R.string.name) to 0,
            resources.getString(
                R.string.surname) to 1,
            resources.getString(
                R.string.account_number) to 2,
            resources.getString(
                R.string.account_name) to 3,
            resources.getString(
                R.string.branch_name) to 4,
            resources.getString(
                R.string.amaount) to 5,
            resources.getString(
                R.string.amount_try) to 6,
            resources.getString(
                R.string.iban) to 7,
            resources.getString(
                R.string.currency) to 8,
            resources.getString(
                R.string.account_type) to 9,
            resources.getString(
                R.string.interest) to 10,
            resources.getString(
                R.string.start_date) to 11,
            resources.getString(
                R.string.end_date)  to 12,
            resources.getString(
                R.string.block)  to 13,
            resources.getString(
                R.string.deleted)  to 14,


            )
        titlesHashMap = hashMapOf(
            "isBlocked" to resources.getString(
                R.string.block),
            "branch" to resources.getString(
                R.string.branch_name),
            "isClosed" to resources.getString(
                R.string.deleted),
            "currency" to resources.getString(
                R.string.currency),
            "interestRate" to resources.getString(
                R.string.interest),
            "balance" to resources.getString(
                R.string.amaount),
            "accountName" to resources.getString(
                R.string.account_name),
            "iban" to resources.getString(
                R.string.iban),
            "balanceAsTRY" to resources.getString(
                R.string.amount_try),
            "closingDate" to  resources.getString(
                R.string.end_date) ,
            "openingDate" to resources.getString(
                R.string.start_date),
            "customerNo" to resources.getString(
                R.string.account_number),
        )
        var rawComing = arrayListOf<String>()
        (requireActivity() as MainActivity).binding.topAppBarToolbar.title=getString(R.string.app_title)

        adapter = AccountDetailAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        Log.d("Info", args.accountDetailFragmentListValues.toString())
        if (!args.accountDetailFragmentListValues.isNullOrEmpty()) {
            Log.d("Info", args.accountDetailFragmentListValues.toString())
            currency = args.accountDetailFragmentListValues!!.substringAfter("currency!")
                .substringBefore("?")
            Log.d("Info", "Currency = ${currency}")
            rawComing.addAll(args.accountDetailFragmentListValues!!.split("?"))
            rawComing.removeAt(rawComing.size - 1)
            for (i in 0..rawComing.size - 1) {
                if (rawComing[i].substringBefore("!").equals("name")) {
                    titles.add(0, resources.getString(
                        R.string.name))
                    values.add(0, rawComing[i].substringAfter("!").substringBefore(" "))
                    titles.add(1, resources.getString(
                        R.string.surname))
                    values.add(1, rawComing[i].substringAfter("!").substringAfter(" "))
                    continue
                }
                val title = rawComing[i].substringBefore("!")
                var value = rawComing[i].substringAfter("!")
                if (value.equals("false")) value = "Hayır"
                else if (value.equals("true")) value = "Evet"
                if (titlesHashMap[title].isNullOrEmpty()) {
                    continue
                }

                if (title.contains(resources.getString(
                        R.string.amount_try)) && currency.equals("TRY")) {
                    continue
                }
                if (title.equals(resources.getString(
                        R.string.start_date))) {
                    titlesHashMap[title]?.let { titles.add(it) }
                    values.add("13 Ekim 2019")
                    continue
                }
                if (title.equals(resources.getString(
                        R.string.end_date) )) {
                    titlesHashMap[title]?.let { titles.add(it) }
                    values.add("29 Kasım 2022")
                    continue
                }

                if (title.contains("alanc")) {
                    titlesHashMap[title]?.let { titles.add(it) }
                    values.add(doubleAmount(value))
                } else if (title.contains("ate")) {
                    titlesHashMap[title]?.let { titles.add(it) }
                    values.add(rate(value))
                } else {
                    titlesHashMap[title]?.let { titles.add(it) }
                    values.add(value)
                }

            }

            if (values[9].equals("% 0.0")) {
                titles.add(resources.getString(
                    R.string.account_type))
                values.add(resources.getString(
                    R.string.vadeli))
            } else {
                titles.add(resources.getString(
                    R.string.account_type))
                values.add(resources.getString(
                    R.string.vadesiz))
            }

            rearrange()
            adapter_ = (binding.recyclerview.adapter as? AccountDetailAdapter)!!
            binding.recyclerview.adapter = adapter_
            adapter_.addAccount(titles, values)

        }

        return binding.root


    }

    private fun doubleAmount(str: String): String {
        return amountFormatter.format(str.toDouble()) + " " + currencySigns[currency]
    }

    private fun rate(str: String): String {
        return "% $str"
    }

    fun rearrange() {
        val AllAccountsArrayList: ArrayList<String> = arrayListOf()
        val values: ArrayList<String> = arrayListOf()
        AllAccountsArrayList.addAll(titles)
        val comparator = Comparator { o1: String, o2: String ->
            return@Comparator roles[o1]!! - roles[o2]!!
        }

        AllAccountsArrayList.sortWith(comparator)
        for (i in AllAccountsArrayList) {
            values.add(this.values.get(titles.indexOf(i)))
        }
        this.values.clear()
        titles.clear()
        this.values.addAll(values)
        titles.addAll(AllAccountsArrayList)
    }

}