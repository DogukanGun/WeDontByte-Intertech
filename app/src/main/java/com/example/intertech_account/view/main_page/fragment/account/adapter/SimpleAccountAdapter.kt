package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.annotation.SuppressLint
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
import com.github.mikephil.charting.data.Entry
import com.itextpdf.text.factories.RomanAlphabetFactory.getString
import java.security.KeyStore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SimpleAccountAdapter : RecyclerView.Adapter<SimpleAccountAdapter.SimpleAccountHolder>() {
    private var transactions: Array<GetAccountTransactionList> = emptyArray()
    private var transactionArrayList: ArrayList<GetAccountTransactionList> = arrayListOf()
    var status: SimpleAccountListState = SimpleAccountListState.NO_FILTER
    private lateinit var simpleAccountFragment: SimpleAccountFragment


    //TODO Strings.xml'den çekilecek
    private var monthMap = hashMapOf<String, String>(
        "01" to "Ocak",
        "02" to "Şubat",
        "03" to "Mart",
        "04" to "Nisan",
        "05" to "Mayıs",
        "06" to "Haziran",
        "07" to "Temmuz",
        "08" to "Ağustos",
        "09" to "Eylül",
        "10" to "Ekim",
        "11" to "Kasım",
        "12" to "Aralık"
    )

    class SimpleAccountHolder(val binding: SimpleAccountRecyclerviewRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    // Ana sayfadaki transactionların response geldikten sonra eklenmesi

    @SuppressLint("NotifyDataSetChanged")
    fun addList(transactions: Array<GetAccountTransactionList>, fragment: SimpleAccountFragment) {
        this.transactions = transactions
        //this.transactions = this.transactions.toCollection(ArrayList()).sortedByDescending { it.date }.toTypedArray()

        transactionArrayList.addAll(transactions)
        val tempTransactionArrayList = lastTenTransactions(transactionArrayList)
        transactionArrayList.clear()
        transactionArrayList.addAll(tempTransactionArrayList)
        simpleAccountFragment = fragment
        notifyDataSetChanged()
/*        var lineChartEntries = ArrayList<Entry>()
        lineChartEntries.add(Entry(2020F, 1000F))
        lineChartEntries.add(Entry(2021F, 1500F))
        lineChartEntries.add(Entry(2023F, 1800F))
        lineChartEntries.add(Entry(2024F, 1200F))
        simpleAccountFragment.updateLineChart(lineChartEntries)*/
    }
    fun getSorted() : ArrayList<GetAccountTransactionList>{
        return transactionArrayList
    }

    @SuppressLint("SimpleDateFormat")
    private fun filterArrayList(dayAmounts: Int): ArrayList<GetAccountTransactionList> {
        return transactionArrayList.filter {
            var transactionArrayListIndexDate = it.date
            transactionArrayListIndexDate = transactionArrayListIndexDate.substring(0, 10)
            val date = SimpleDateFormat("dd-MM-yyyy").parse(transactionArrayListIndexDate)!!
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, dayAmounts)
//                        val currentDate = SimpleDateFormat("dd-MM-yyyy").parse((Calendar.DAY_OF_MONTH.toString()+"-"+Calendar.MONTH.toString()+"-"+Calendar.YEAR.toString()))
            date.after(calendar.time)
        } as ArrayList<GetAccountTransactionList>
    }

    @SuppressLint("SimpleDateFormat", "NotifyDataSetChanged")
    fun changeStatusOfArray() {
        transactionArrayList.clear()
        transactionArrayList.addAll(transactions)
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
//                    transactionArrayList=filterArrayList(transactionArrayList,SimpleAccountListState.DETAIL.day)
            }
            SimpleAccountListState.NO_FILTER -> {
//                    transactionArrayList=filterArrayList(transactionArrayList,SimpleAccountListState.NO_FILTER.day)
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


    //Transactionların doldurulması

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

            holder.binding.explanation.text = transactionArrayList[position].explanation
            holder.binding.dateDay.text = day
            holder.binding.dateMonth.text = monthMap[month]
            holder.binding.dateTime.text = transactionArrayList[position].time
            holder.binding.amountValue.text =
                Constant.amountFormatter.format(transactionArrayList[position].amount)+ " " + transactionArrayList[position].currencyCode
            holder.binding.aliciIsmi.text = transactionArrayList[position].userCode

            if (transactionArrayList[position].amount > 0) {
                holder.binding.amountValue.setTextColor(Color.BLACK)
            } else {
                holder.binding.amountValue.setTextColor(Color.RED)
            }
        }
    }

    override fun getItemCount(): Int {
        return transactionArrayList.size
    }

//    private fun createDummyTransactionList(x: Double, d: String): GetAccountTransactionList {
//        var x = GetAccountTransactionList("test",d,"test","test",x,122.2,
//            "t","t","t","t","05-05-2010","t","t",
//            233.3,"t"
//        )
//        return x
//    }

    private fun lastTenTransactions(item: ArrayList<GetAccountTransactionList>): ArrayList<GetAccountTransactionList> {
        //val allTransactionsArrayList: ArrayList<GetAccountTransactionList> = arrayListOf()

        var allTransactionsArrayList = item.sortedByDescending { it.date }.toCollection(ArrayList())
        if (item.size >= 10) {
            allTransactionsArrayList = allTransactionsArrayList.take(10).toCollection(ArrayList())
            /*for (i in 0 until 10) {
                allTransactionsArrayList.add(item[i])
                Log.d("info ", "added ${item[i].date}")
            }*/
        } /*else {
           /* for (i in 0 until item.size) {
                allTransactionsArrayList.add(item[i])
                Log.d("info ", "added ${item[i].date}")
            }*/
        }*/

        return allTransactionsArrayList

    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortTransactionsDefault() {
        transactionArrayList.clear()
        transactionArrayList.addAll(lastTenTransactions(transactions.toCollection(ArrayList())))
        notifyDataSetChanged()
    }


}

