package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.HomeScreenTransactionRowBinding
import com.example.intertech_account.databinding.SimpleAccountRecyclerviewRowBinding
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionList
import com.example.intertech_account.model.api_model.status.SimpleAccountListState
import com.example.intertech_account.resources.common_variables.Receipt
import com.example.intertech_account.view_model.GetReceiptViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SimpleAccountAdapter : RecyclerView.Adapter<SimpleAccountAdapter.SimpleAccountHolder>() {
    private var transactions: Array<GetAccountTransactionList> = emptyArray()
    private var transactionArrayList: ArrayList<GetAccountTransactionList> = arrayListOf()
    var status: SimpleAccountListState = SimpleAccountListState.NO_FILTER

    class SimpleAccountHolder(val binding: SimpleAccountRecyclerviewRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    // Ana sayfadaki transactionların response geldikten sonra eklenmesi

    @SuppressLint("NotifyDataSetChanged")
    fun addList(transactions: Array<GetAccountTransactionList>) {
        this.transactions = transactions
        transactionArrayList.addAll(transactions)
        notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    private fun filterArrayList(dayAmounts: Int): ArrayList<GetAccountTransactionList> {
        return transactionArrayList.filter {

            val date = SimpleDateFormat("dd-MM-yyyy").parse(it.time)!!
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
//                holder.binding.showDekont.setOnClickListener {
////                    Receipt.referenceNo=transactions[position].referenceNumber.toInt()
//                    Receipt.isReceiptButtonClicked.value=true
////                    Receipt.branchCode=transactions[position].branchCode.toInt()
////                    Receipt.customerNo=transactions[position].customerNo.toInt()
////                    Receipt.transactionDate=transactions[position].time
//
//                }
            holder.binding.explanation.text = transactionArrayList[position].explanation
            holder.binding.dateValue.text = transactionArrayList[position].date
            holder.binding.dateTime.text = transactionArrayList[position].time
            // TODO currency kodların dinamik olarak gelmesi
            holder.binding.amountValue.text = transactionArrayList[position].amount.toString()
            // TODO alici ismi yerine user code gelmeli
            holder.binding.aliciIsmi.text = transactionArrayList[position].userCode

            if (transactionArrayList[position].amount > 0) {
                holder.binding.amountValue.setTextColor(Color.BLACK)
            } else {
                holder.binding.amountValue.setTextColor(Color.RED)
            }
            holder.binding.aliciIsmi.text = transactionArrayList[position].type
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
        val allTransactionsArrayList: ArrayList<GetAccountTransactionList> = arrayListOf()
        item.sortWith(compareByDescending { it.date })
        if (item.size >= 10) {
            for (i in 0 until 10) {
                allTransactionsArrayList.add(item[i])
            }
        } else {
            for (i in 0 until item.size) {
                allTransactionsArrayList.add(item[i])
            }
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

