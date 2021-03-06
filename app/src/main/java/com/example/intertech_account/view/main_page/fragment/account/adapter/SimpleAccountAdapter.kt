package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.R
import com.example.intertech_account.databinding.SimpleAccountRecyclerviewRowBinding
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionList
import com.example.intertech_account.model.api_model.status.SimpleAccountListState
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.resources.common_variables.Receipt
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view.main_page.fragment.account.SimpleAccountFragment
import com.example.intertech_account.view_model.repo.DateConvert
import com.github.mikephil.charting.data.Entry
import com.itextpdf.text.factories.RomanAlphabetFactory.getString
import java.security.KeyStore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class SimpleAccountAdapter(var context: Context) : RecyclerView.Adapter<SimpleAccountAdapter.SimpleAccountHolder>() {
    private var transactions: Array<GetAccountTransactionList> = emptyArray()
    private var transactionArrayList: ArrayList<GetAccountTransactionList> = arrayListOf()
    var status: SimpleAccountListState = SimpleAccountListState.NO_FILTER
    private lateinit var simpleAccountFragment: SimpleAccountFragment
    private val dateConvert=DateConvert(context)



    class SimpleAccountHolder(val binding: SimpleAccountRecyclerviewRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    // Ana sayfadaki transactionlar??n response geldikten sonra eklenmesi

    @SuppressLint("NotifyDataSetChanged")
    fun addList(transactions: Array<GetAccountTransactionList>, fragment: SimpleAccountFragment) {
        this.transactions = transactions

        transactionArrayList.addAll(transactions)
        val tempTransactionArrayList = lastTenTransactions(transactionArrayList)
        transactionArrayList.clear()
        transactionArrayList.addAll(tempTransactionArrayList)
        simpleAccountFragment = fragment
        notifyDataSetChanged()

    }
    fun getSorted() : ArrayList<GetAccountTransactionList>{
        return transactionArrayList
    }

    private fun filterArrayList(dayAmounts: Int): ArrayList<GetAccountTransactionList> {
        Log.d("Info:","G??n"+dayAmounts.toString())
        var orgArr =ArrayList<GetAccountTransactionList>()
        orgArr.addAll(transactions)
        orgArr.reverse()
        var retArr =ArrayList<GetAccountTransactionList>()
        val calendar = Calendar.getInstance()
        for(i in 0..orgArr.size-1){
            val date = SimpleDateFormat("yyyy-MM-dd").parse(orgArr[i].date.substring(0, 10))!!
            if(calendar.timeInMillis-date.time <= TimeUnit.DAYS.toMillis(dayAmounts.toLong())){
                retArr.add(orgArr.get(i))

            }

        }
        return retArr

    }

    @SuppressLint("SimpleDateFormat", "NotifyDataSetChanged")
    fun changeStatusOfArray() {
        var backupArray =ArrayList<GetAccountTransactionList>()
        backupArray.addAll(transactionArrayList)
        transactionArrayList.clear()

        when (status) {
            SimpleAccountListState.LAST_ONE_WEEK -> {
                transactionArrayList = filterArrayList(SimpleAccountListState.LAST_ONE_WEEK.day)
            }
            SimpleAccountListState.LAST_ONE_MONTH -> {
                transactionArrayList = filterArrayList(SimpleAccountListState.LAST_ONE_MONTH.day)
            }
            SimpleAccountListState.LAST_THREE_MONTHS -> {
                transactionArrayList = filterArrayList(SimpleAccountListState.LAST_THREE_MONTHS.day)
            }
            SimpleAccountListState.LAST_SIX_MONTHS -> {
                transactionArrayList = filterArrayList(SimpleAccountListState.LAST_SIX_MONTHS.day)
            }
            SimpleAccountListState.LAST_ONE_YEAR -> {
                transactionArrayList = filterArrayList(SimpleAccountListState.LAST_ONE_YEAR.day)
            }
            SimpleAccountListState.DETAIL -> {
                    transactionArrayList=lastTenTransactions(backupArray)
            }
            SimpleAccountListState.NO_FILTER -> {
//                    transactionArrayList.addAll(transactions)
            }
        }
        notifyDataSetChanged()


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleAccountHolder {
        val binding = SimpleAccountRecyclerviewRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SimpleAccountHolder(binding)
    }


    //Transactionlar??n doldurulmas??

    override fun onBindViewHolder(holder: SimpleAccountHolder, position: Int) {

        if (transactionArrayList.isNotEmpty()) {
            //butonun ide receiptButton olmali
            simpleAccountFragment.executeSharePopupMenu(simpleAccountFragment.layoutInflater, holder)
            /*holder.binding.showDekont.setOnClickListener {
                Receipt.referenceNo=3411
                Receipt.isReceiptButtonClicked.value=true
                Receipt.branchCode=9142
                Receipt.customerNo=123
                Receipt.transactionDate="2021-01-28"

            }*/
            var year = transactionArrayList[position].date.substringBefore("T").substring(0, 4)
            var month = transactionArrayList[position].date.substringBefore("T").substring(5, 7)
            var day = transactionArrayList[position].date.substringBefore("T").substring(8, 10)

            holder.binding.apply {
                explanation.text = transactionArrayList[position].explanation
                dateDay.text = day
                dateMonth.text = dateConvert.convertDate(month)
                dateTime.text = year
                aliciIsmi.text = transactionArrayList[position].userCode

                if(transactionArrayList[position].amount < 0)
                {
                    amountValue.text =
                        Constant.amountFormatter.format(transactionArrayList[position].amount)+ " " + transactionArrayList[position].currencyCode
                }
                else
                {
                    amountValue.text =
                        "+" + Constant.amountFormatter.format(transactionArrayList[position].amount)+ " " + transactionArrayList[position].currencyCode
                }
            }

            if (transactionArrayList[position].amount > 0) {
                holder.binding.amountValue.setTextColor(Color.GREEN)
            } else {
                holder.binding.amountValue.setTextColor(Color.RED)
            }
        }
    }

    override fun getItemCount(): Int {
        return transactionArrayList.size
    }


    private fun lastTenTransactions(item: ArrayList<GetAccountTransactionList>): ArrayList<GetAccountTransactionList> {

        var allTransactionsArrayList = item.sortedByDescending { it.date }.toCollection(ArrayList())
        if (item.size >= 10) {
            allTransactionsArrayList = allTransactionsArrayList.take(10).toCollection(ArrayList())
        }


        return allTransactionsArrayList

    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortTransactionsDefault() {
        transactionArrayList.clear()
        transactionArrayList.addAll(lastTenTransactions(transactions.toCollection(ArrayList())))
        notifyDataSetChanged()
    }


}

